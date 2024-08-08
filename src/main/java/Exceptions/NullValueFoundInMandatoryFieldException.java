package Exceptions;

import models.BaseModel;


public class NullValueFoundInMandatoryFieldException extends RuntimeException{

    public NullValueFoundInMandatoryFieldException( String message ){

        super( message );
    }

}