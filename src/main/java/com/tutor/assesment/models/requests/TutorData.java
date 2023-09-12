package com.tutor.assesment.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TutorData {

    private String tutorId;
    private List<String> kindOfExperience;
    private String totalExperience;

}
