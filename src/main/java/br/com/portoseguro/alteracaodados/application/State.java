package br.com.portoseguro.alteracaodados.application;

public interface State {

    OutputState execute(InputState inputState);
}