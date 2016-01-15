package gamebuddy.game.hackshow.core.parser;

import java.util.List;

/**
 * describe
 * created by tindle
 * created time 16/1/15 下午12:26
 */
public class MainParser {


    public List<String> processArgs(String[] args) {
        int ac=0;
        while(ac < args.length){
            String flag = args[ac];
            ac++;

            char ch = flag.charAt(0);
            switch(ch) {
                case 'l':

                    break;
                case 'h':

                    break;
                case 's':

                    break;
            }

        }

        return null;
    }
}
