package com.abroad.baekjunghyunDev.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessRequestDto {
    private List<Integer> grantUserIds;  // 권한을 부여할 유저들의 ID 리스트
    private List<Integer> revokeUserIds; // 권한을 취소할 유저들의 ID 리스트
}
