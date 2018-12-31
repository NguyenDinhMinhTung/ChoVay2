package com.example.megas.chovay;

import com.example.megas.chovay.DTO.GroupDTO;
import com.example.megas.chovay.DTO.PeopleDTO;

import java.util.List;

public class Util {
    public static String[] listPeopleToStringArray(List<PeopleDTO> list) {
        String[] result = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i).getName();
        }

        return result;
    }

    public static String[] listGroupToStringArray(List<GroupDTO> list) {
        String[] result = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i).getName();
        }

        return result;
    }
}
