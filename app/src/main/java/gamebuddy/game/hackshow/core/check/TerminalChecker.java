package gamebuddy.game.hackshow.core.check;

/**
 * describe
 * created by tindle
 * created time 16/1/14 下午1:01
 */
public class TerminalChecker {

    public interface Callback {
        void onBackResult(String result);
    }

    Callback mCallback;

    public TerminalChecker(Callback callback){
        this.mCallback = callback;
    }

    public void firstCheck(String content){
        if(content.startsWith("ls")){
            goCheckLs(content);
            return;
        }

        if(content.startsWith("chmod")){
            return;
        }

        if(content.startsWith("scan")){
            putBack("Begin scanning...");
            return;
        }

        putBack("command not found");
    }

    public void goCheckLs(String result){
        if(result.contains("-s")){

        }

        if(result.contains("-a")){

        }

        if(result.contains("-a")){

        }
    }

    public void putBack(String result){
        if(mCallback==null){
            return;
        } else {
            mCallback.onBackResult(result);
        }
    }

}
