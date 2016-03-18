class Signal {
    boolean green = true;

    public boolean getState() {
        return green;
    }

    public void changeState() {
        if(green) {
            green = false;         
        } else {
            green = true;
        }
    }
}