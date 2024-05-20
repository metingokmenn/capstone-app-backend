package com.customer_analysis.age_detection.model;

import java.util.Objects;

public class AgeGroup implements Comparable<AgeGroup> {
    private final String ageGroup;

    public String getAgeGroup() {
        return ageGroup;
    }

    public AgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    @Override
    public int compareTo(AgeGroup other) {
        // Yaş gruplarını karşılaştırmak için sıralama mantığını burada belirleyin
        // Örneğin, yaş gruplarını başlangıç yaşına göre sıralayabilirsiniz
        // Bu örnekte, yaş grupları formatı "0-2", "8-12" gibi olduğu varsayılmıştır.
        int startAgeThis = Integer.parseInt(this.ageGroup.split("-")[0]);
        int startAgeOther = Integer.parseInt(other.ageGroup.split("-")[0]);
        return Integer.compare(startAgeThis, startAgeOther);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgeGroup ageGroup1 = (AgeGroup) o;
        return Objects.equals(ageGroup, ageGroup1.ageGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ageGroup);
    }

 
    
}
