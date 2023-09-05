package br.ematos.chatgpt.calculator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UnitConverterService {

    private static final Map<Unit, UnitInfo> unitInfoMap = new HashMap<Unit, UnitInfo>() {{
        put(Unit.G, new UnitInfo(Unit.KG, 1.0 / 1000.0));
        put(Unit.KG, new UnitInfo(Unit.G, 1000.0));
        put(Unit.LB, new UnitInfo(Unit.KG, 0.45359237));
        put(Unit.ML, new UnitInfo(Unit.L, 1.0 / 1000.0));
        put(Unit.L, new UnitInfo(Unit.ML, 1000.0));
        put(Unit.OZ, new UnitInfo(Unit.L, 1.0 / 33.814));
        put(Unit.M, new UnitInfo(Unit.CM, 100.0));
        put(Unit.CM, new UnitInfo(Unit.M, 1.0 / 100.0));
        put(Unit.MM, new UnitInfo(Unit.M, 1.0 / 1000.0));
    }};

    public double convertToBaseUnit(double value, String unit) {
        double result = 0.0;
        if (unitInfoMap.containsKey(Unit.valueOfUnitName(unit.toLowerCase()))) {
            UnitInfo unitInfo = unitInfoMap.get(Unit.valueOfUnitName(unit.toLowerCase()));
            double conversionFactor = unitInfo.getConversionFactor();
            result = value * conversionFactor;
        } else {
            result = value;
        }
        return result;
    }

    public double calculatePrice(double priceInCAD, double valueInBaseUnit) {
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.US);
        DecimalFormat df = new DecimalFormat("#.###", symbols);
        return Double.parseDouble(df.format(priceInCAD / valueInBaseUnit));
    }

    public Unit getBaseUnit(Unit unit) {
        if (unitInfoMap.containsKey(unit)) {
            return unitInfoMap.get(unit).getBaseUnit();
        }
        return unit;
    }

    public String convertAndPrint(double itemPriceInCAD, double itemValue, String unit) {
        double valueInBaseUnit = convertToBaseUnit(itemValue, unit);
        double pricePerBaseUnit = calculatePrice(itemPriceInCAD, valueInBaseUnit);
        Unit baseUnit = getBaseUnit(Unit.valueOfUnitName(unit));

        System.out.println("Item Price in CAD: " + itemPriceInCAD);
        System.out.println("Item Value: " + itemValue + " " + unit);
        System.out.println("Value in Base Unit: " + valueInBaseUnit + " " + baseUnit);
        System.out.println("Price per Base Unit: " + pricePerBaseUnit + " CAD/" + baseUnit);

        return pricePerBaseUnit + " CAD/" + baseUnit;
    }

    public static void main(String[] args) {
        double itemPriceInCAD = 10.0;
        double itemValue = 1.0;
        String unit = "lb";

        new UnitConverterService().convertAndPrint(itemPriceInCAD, itemValue, unit);
    }
}