package com.surgar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
//分页结果类
public class PageResult<T> {
    private Long total;
    private List<T> rows;
}
