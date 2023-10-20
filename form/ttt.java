
package form;

class ttt implements Runnable {
    MonitorActualizarVentana m;

    public ttt(final MonitorActualizarVentana m, final Object ttt) {
        final String s = (String) ttt;
        this.m = m;
        switch (s) {
            case "Insta": {
                m.setSpeed(5);
                break;
            }
            case "Max": {
                m.setSpeed(165);
                break;
            }
            case "Med": {
                m.setSpeed(200);
                break;
            }
            case "Min": {
                m.setSpeed(250);
                break;
            }
        }
    }

    @Override
    public void run() {
        if (this.m.isTimerOn()) {
            this.m.startTimer();
        }
    }
}
