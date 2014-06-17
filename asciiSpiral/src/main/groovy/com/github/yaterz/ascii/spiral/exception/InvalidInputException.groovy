package com.github.yaterz.ascii.spiral.exception

/**
 * InvalidInputException
 *
 * @author mattyaterz
 * @since 2014-06-17
 */
class InvalidInputException extends Exception {
    InvalidInputException( String msg ) {
        super( "Invalid input: $msg" )
    }

    InvalidInputException( String msg, Throwable throwable ) {
        super( "Invalid input: $msg", throwable )
    }
}
