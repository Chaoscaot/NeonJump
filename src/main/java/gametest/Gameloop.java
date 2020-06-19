package gametest;

public class Gameloop implements Runnable{

    Game game;
    boolean running;
    int target;
    int ips, realips;
    Runnable iterator;
    Thread thread;
    boolean debug;
    String name;

    public Gameloop(Game game, int target, Runnable iterator, String name, boolean debug){
        this.game = game;
        this.target = target;
        this.iterator = iterator;
        thread = new Thread(this, "GameTread-" + name);
        this.debug = debug;
        this.name = name;
    }

    public Gameloop(Game game, int target, Runnable iterator, String name){
        this(game, target, iterator, name, false);

    }

    @Override
    public void run() {
        double nsPerTick = 1000000000.0 / target;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double unprocessed = 0.0;
        System.out.println(this.toString() + " Started!");

        while (running) {
            nsPerTick = 1000000000.0 / target;
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;

            if(unprocessed >= 1.0){
                iterator.run();
                unprocessed--;
                ips++;
            }

            try {
                if(!(ips < 0)) Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if(System.currentTimeMillis() - 1000 > timer){
                timer += 1000;
                if(debug) System.out.println(name + ": " +  ips);
                realips = ips;
                ips = 0;
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int ips) {
        this.target = ips;
    }

    public void startLoop() {
        if(running) return;
        running = true;
        this.thread.start();
    }

    public void stopLoop() {
        if(!running) return;
        running = false;
    }

    public int getIps() {
        return realips;
    }

    public void setIps(int ips) {
        this.ips = ips;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isDebug() {
        return debug;
    }
}
