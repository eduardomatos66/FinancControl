package br.ematos.chatgpt.calculator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UnitInfo {
    private Unit baseUnit;
    private double conversionFactor;
}
