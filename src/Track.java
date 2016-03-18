class Track {
    boolean activ = false;

    public Track() {
    }

    public boolean getState() {
        return activ;
    }

    public void changeState() {
        if(activ == true) {
            activ = false;
        } else {
            activ = true;
        }
    }

}