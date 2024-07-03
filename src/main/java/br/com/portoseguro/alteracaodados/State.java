package br.com.portoseguro.alteracaodados;

public interface State {

    OutputState execute(InputState inputState);
}