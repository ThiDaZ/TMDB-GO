package com.thidas.tmdbgo.exception;

import org.springframework.shell.command.CommandExceptionResolver;
import org.springframework.shell.command.CommandHandlingResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

@Component
public class TmdbGoExceptionResolver implements CommandExceptionResolver {
    @Override
    public CommandHandlingResult resolve(Exception ex) {

        if(ex instanceof ResourceAccessException){
            return CommandHandlingResult.of(
                    "\n ❌ERROR: Can't connect to TMBD \n" +
                            "-> Please check your internet connection. \n",
                    1
            );
        }

        if(ex instanceof HttpClientErrorException.Unauthorized){
            return CommandHandlingResult.of(
                    "\n ❌ERROR: Invalid API key. \n" +
                            "Please check your TMDB_API_KEY environment variable. \n" +
                            "Ensure it is set in application.properties.",
                    1
            );
        }

        if(ex instanceof HttpClientErrorException.NotFound){
            return CommandHandlingResult.of(
                    "\n ❌ERROR: The requested resource not found on TMDB. \n",
                    1
            );
        }

        return null;
    }
}
