package com.molinosystem.sistema_molino.requests;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class SuplyUsageSearchRequest {
    private Long suplyId;
    private Timestamp startDate;
    private Timestamp endDate;
}
