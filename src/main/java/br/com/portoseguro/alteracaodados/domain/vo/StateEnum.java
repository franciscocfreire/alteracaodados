package br.com.portoseguro.alteracaodados.domain.vo;

public enum StateEnum {
    INITIAL {
        @Override
        public StateEnum nextState() {
            return FACIAL_BIOMETRIC;
        }
    },
    FACIAL_BIOMETRIC {
        @Override
        public StateEnum nextState() {
            return AUTHENTICATOR;
        }
    },
    AUTHENTICATOR {
        @Override
        public StateEnum nextState() {
            return CHANGE_DATA;
        }
    },
    CHANGE_DATA {
        @Override
        public StateEnum nextState() {
            return DONE;
        }
    },
    DONE {
        @Override
        public StateEnum nextState() {
            return null;
        }
    };

    public abstract StateEnum nextState();
}
