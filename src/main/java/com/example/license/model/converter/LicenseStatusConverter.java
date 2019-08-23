package com.example.license.model.converter;

import com.example.license.model.enums.LicenseStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class LicenseStatusConverter implements AttributeConverter<LicenseStatus, String> {

    @Override
    public String convertToDatabaseColumn(LicenseStatus LicenseStatus) {
        if (LicenseStatus == null) {
            return null;
        }
        return LicenseStatus.getCode();
    }

    @Override
    public LicenseStatus convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(LicenseStatus.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
