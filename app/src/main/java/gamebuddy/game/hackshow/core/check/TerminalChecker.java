package gamebuddy.game.hackshow.core.check;

/**
 * describe
 * created by tindle
 * created time 16/1/14 下午1:01
 */
public class TerminalChecker {

    interface Callback {
        String onBackResult();
    }

    Callback mCallback;

    public TerminalChecker(Callback callback){
        this.mCallback = callback;
    }

    public void firstCheck(String result){
        if(result.startsWith("ls")){
            goCheckLs(result);
        }

        if(result.startsWith("chmod")){

        }
    }

    public void goCheckLs(String result){
        if(result.contains("-s")){

        }

        if(result.contains("-a")){

        }

        if(result.contains("-a")){

        }
    }

}
