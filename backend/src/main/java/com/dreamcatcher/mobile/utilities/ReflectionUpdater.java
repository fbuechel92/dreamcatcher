package com.dreamcatcher.mobile.utilities;

import com.dreamcatcher.mobile.entity.User;
import jakarta.transaction.Transactional;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ReflectionUpdater {

    private final PasswordEncoder passwordEncoder;

    // Constructor injection for PasswordEncoder
    public ReflectionUpdater(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //Method identifies getter and setter methods, then invokes the setter IF there was a change
    @Transactional
    public boolean updateFields(User currentUser, User submittedUser){

        String fieldName;
        String setterName;
        boolean changeDetected = false;

        // Those fields should be excluded from any changes
        List<String> excludedFields = Arrays.asList("getClass", "getUserId", "getPassword");

        try{
            //Identify field names
            Method[] methods = currentUser.getClass().getMethods();

            for(Method method:methods){
                if(method.getName().substring(0,3).equals("get") && excludedFields.stream().noneMatch(method.getName()::equals)) {

                    //Determine getter and setter
                    fieldName = method.getName().substring(3);
                    setterName = "set" + fieldName;
                    Method setter = currentUser.getClass().getMethod(setterName, method.getReturnType());
                    Method getter = method;

                    //Compare values
                    Object currentValue = getter.invoke(currentUser);
                    Object submittedValue;

                    if(getter.getName().equals("getPassword")){
                        submittedValue = passwordEncoder.encode(submittedUser.getPassword());
                    } else {
                        submittedValue = getter.invoke(submittedUser);
                    }

                    if(!Objects.equals(currentValue, submittedValue)){
                        setter.invoke(currentUser, submittedValue);
                        changeDetected = true;
                    }
                }
            }
            return changeDetected;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("The updateFields method in the ReflectionUpdater class threw an exception");
        }
    }
}