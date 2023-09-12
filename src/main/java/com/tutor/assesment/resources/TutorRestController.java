package com.tutor.assesment.resources;

import com.tutor.assesment.models.Tutor;
import com.tutor.assesment.models.requests.TutorData;
import com.tutor.assesment.services.ScoreStrategy;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Process the incoming request and calculate the score for the give tutor
 *
 * @Author: Kamal
 */

@RestController
@RequestMapping(TutorRestController.V1_PATH)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TutorRestController {

    public static final String V1_PATH = "tutor/v1";

    private final ScoreStrategy scoreStrategy;

    @PostMapping(value = "/calculate")
    @ApiOperation(value = "Calculate scores", response = Tutor.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully calculated the scores"),
            @ApiResponse(code = 400, message = "Application failed to process the request")
    })
    public ResponseEntity<Tutor> handleTutorDataAndCalculateScores(@RequestBody TutorData tutorData) {
        return ResponseEntity.ok(scoreStrategy.calculateScore(tutorData));
    }
}
