package fuente;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class TypefacedTextView extends android.support.v7.widget.AppCompatTextView{

    public TypefacedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface roboto = Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto-Bold.ttf");
        setTypeface(roboto);


    }

}