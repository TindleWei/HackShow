package gamebuddy.game.hackshow.core.provider;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@IntDef({
        ClickType.HEADER_FIRST_CLICKED,
        ClickType.DOC_ARTICLE_CLICKED
})
public @interface ClickType {
    int HEADER_FIRST_CLICKED = 1000;

    int DOC_ARTICLE_CLICKED = 1001;
}
