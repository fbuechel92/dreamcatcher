package com.dreamcatcher.mobile.utilities;

import com.dreamcatcher.mobile.entity.User;
import jakarta.transaction.Transactional;
import java.lang.reflect.Method;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class ReflectionUpdater {

    //Method identifies getter and setter methods, then invokes the setter IF there was a change
    @Transactional
    public boolean updateFields(User currentUser, User submittedUser){

        String fieldName;
        String setterName;
        boolean changeDetected = false;

        try{
            //Identify field names
            Method[] methods = currentUser.getClass().getMethods();

            for(Method method:methods){
                if(method.getName().substring(0,3).equals("get")){

                    //Determine getter and setter
                    fieldName = method.getName().substring(3);
                    setterName = "set" + fieldName;
                    Method setter = currentUser.getClass().getMethod(setterName, method.getReturnType());
                    Method getter = method;

                    //Compare values
                    Object currentValue = getter.invoke(currentUser);
                    Object submittedValue = getter.invoke(submittedUser);

                    if(!Objects.equals(currentValue, submittedValue)){
                        setter.invoke(currentUser, submittedValue);
                        changeDetected = true;
                    }
                }
            }
            return changeDetected;
        } catch (Exception e) {
            throw new RuntimeException("We had trouble saving this :-(");
        }
    }
}