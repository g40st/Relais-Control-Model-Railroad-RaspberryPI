class Switch {
    boolean straight = true;

    public boolean getState() {
        return straight;
    }

    public void changeState() {
        if(straight) {
            straight = false;
        } else {
            straight = true;
        }
    }
}