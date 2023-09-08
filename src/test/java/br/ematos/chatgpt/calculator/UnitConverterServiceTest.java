package br.ematos.chatgpt.calculator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UnitConverterServiceTest {

  @Test
  void testConvertToBaseUnit() {
    UnitConverterService unitConverterService = new UnitConverterService();
    double value = 100.0;
    String unit = "lb";

    Map<Unit, UnitInfo> mockUnitInfoMap = mock(Map.class);
    when(mockUnitInfoMap.containsKey(Unit.valueOfUnitName(unit.toLowerCase()))).thenReturn(true);
    when(mockUnitInfoMap.get(Unit.valueOfUnitName(unit.toLowerCase())))
        .thenReturn(new UnitInfo(Unit.KG, 0.45359237));

    double result = unitConverterService.convertToBaseUnit(value, unit);

    assertEquals(45.359237, result, 0.001);
  }

  @Test
  void testCalculatePrice() {
    // Arrange
    UnitConverterService unitConverterService = new UnitConverterService();
    double priceInCAD = 10.0;
    double valueInBaseUnit = 2.5;

    // Act
    double result = unitConverterService.calculatePrice(priceInCAD, valueInBaseUnit);

    // Assert
    assertEquals(4.0, result, 0.001);
  }

  @Test
  void testGetBaseUnit() {
    // Arrange
    UnitConverterService unitConverterService = new UnitConverterService();
    Unit unit = Unit.LB;

    // Mock the unitInfoMap
    Map<Unit, UnitInfo> mockUnitInfoMap = mock(Map.class);
    when(mockUnitInfoMap.containsKey(unit)).thenReturn(true);
    when(mockUnitInfoMap.get(unit)).thenReturn(new UnitInfo(Unit.KG, 0.45359237));

    // Act
    Unit result = unitConverterService.getBaseUnit(unit);

    // Assert
    assertEquals(Unit.KG, result);
  }

  @Test
  void testConvertAndPrintWithValidUnit() {
    // Arrange
    UnitConverterService unitConverterService = new UnitConverterService();
    double itemPriceInCAD = 10.0;
    double itemValue = 1.0;
    String unit = "lb";

    // Mock the unitInfoMap
    Map<Unit, UnitInfo> mockUnitInfoMap = mock(Map.class);
    when(mockUnitInfoMap.containsKey(Unit.valueOfUnitName(unit.toLowerCase()))).thenReturn(true);
    when(mockUnitInfoMap.get(Unit.valueOfUnitName(unit.toLowerCase())))
        .thenReturn(new UnitInfo(Unit.KG, 0.45359237));

    // Act
    String result = unitConverterService.convertAndPrint(itemPriceInCAD, itemValue, unit);

    // Assert
    String expectedOutput = "22.046 CAD/KG";
    assertEquals(expectedOutput, result);
  }

  @ParameterizedTest
  @CsvSource({
    "lb, 10.0, 1.0, '22.046 CAD/KG'",
    "oz, 10.0, 1.0, '338.14 CAD/L'",
    "g, 10.0, 1.0, '10000.0 CAD/KG'",
    "ml, 10.0, 1.0, '10000.0 CAD/L'",
    "cm, 10.0, 1.0, '1000.0 CAD/M'",
    "mm, 10.0, 1.0, '10000.0 CAD/M'"
  })
  void testConvertAndPrintWithUnitInfoMapEntries(
      String unit, double itemPriceInCAD, double itemValue, String expectedOutput) {
    // Arrange
    UnitConverterService unitConverterService = new UnitConverterService();

    // Mock the unitInfoMap
    Map<Unit, UnitInfo> mockUnitInfoMap = mock(Map.class);
    when(mockUnitInfoMap.containsKey(Unit.valueOfUnitName(unit.toLowerCase()))).thenReturn(true);
    when(mockUnitInfoMap.get(Unit.valueOfUnitName(unit.toLowerCase())))
        .thenReturn(new UnitInfo(Unit.KG, 0.45359237));

    // Act
    String result = unitConverterService.convertAndPrint(itemPriceInCAD, itemValue, unit);

    // Assert
    assertEquals(expectedOutput, result);
  }
}
