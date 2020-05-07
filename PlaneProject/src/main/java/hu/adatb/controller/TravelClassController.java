package hu.adatb.controller;

import hu.adatb.dao.TravelClassDao;
import hu.adatb.dao.TravelClassDaoImpl;
import hu.adatb.model.TravelClass;
import hu.adatb.utils.DataHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TravelClassController {
    private TravelClassDao dao = new TravelClassDaoImpl();
    private static TravelClassController single_instance = null;


    public static TravelClassController getInstance(){
        if(single_instance == null){
            single_instance = new TravelClassController();
        }
        return single_instance;
    }

    public List<TravelClass> getAll() {
        return dao.getAll();
    }

    public ArrayList<DataHelper> getCountOfTicketGroupByTravelClass() {
        var dictionary = dao.getCountOfTicketGroupByTravelClass();
        var travelClasses = Arrays.asList("Turista", "Business", "First");

        for (var travelClass: travelClasses) {
            var touristDictionary = dictionary.stream().filter(data -> data.travelClassName.equals(travelClass)).collect(Collectors.toList());
            ArrayList<Integer> month = new ArrayList<>();

            for (var item: touristDictionary) {
                month.add(item.month);
            }

            for(int i = 4; i < 7; i++) {
                if(!month.contains(i)) {
                    dictionary.add(new DataHelper(travelClass, i, 0));
                }
            }
        }

        return dictionary;
    }
}
