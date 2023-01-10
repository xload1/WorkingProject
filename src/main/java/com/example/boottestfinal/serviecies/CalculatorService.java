package com.example.boottestfinal.serviecies;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CalculatorService {
    public String calculate(String input){
        List<String> nums = Arrays.stream(input.split("[-+*/]")).collect(Collectors.toList());
        List<String> characters = Arrays.stream(input.split("\\d+")).filter(e->!e.equals("")).collect(Collectors.toList());
        int multiplicationIndex = characters.indexOf("*");
        try {
            while (multiplicationIndex >= 0) {
                nums.set(multiplicationIndex, String.valueOf(Integer.parseInt(nums.get(multiplicationIndex)) *
                        Integer.parseInt(nums.get(multiplicationIndex + 1))));
                nums.remove(multiplicationIndex + 1);
                characters.remove(multiplicationIndex);
                multiplicationIndex = characters.indexOf("*");
            }
            int divisionIndex = characters.indexOf("/");
            while (divisionIndex >= 0) {
                nums.set(divisionIndex, String.valueOf(Integer.parseInt(nums.get(divisionIndex)) /
                        Integer.parseInt(nums.get(divisionIndex + 1))));
                nums.remove(divisionIndex + 1);
                characters.remove(divisionIndex);
                divisionIndex = characters.indexOf("/");
            }
            characters.add(0, "+");
            for (int i = 0; i < nums.size(); i++) {
                if (Objects.equals(characters.get(i), "-")) nums.set(i, String.valueOf(-Integer.parseInt(nums.get(i))));
            }
        }catch (Exception e){
            return "error";
        }
        return String.valueOf(nums.stream().mapToInt(Integer::parseInt).sum());
    }
}
