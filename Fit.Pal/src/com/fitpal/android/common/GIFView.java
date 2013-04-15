package com.fitpal.android.common;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.view.View;

public class GIFView extends View{
	
	Movie movie;
	InputStream is=null;
	long moviestart;

	public GIFView(Context context, int imageId) {
		super(context);
		is=context.getResources().openRawResource(imageId);
		movie=Movie.decodeStream(is);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(0xFFCCCCCC);
		super.onDraw(canvas);
		long now=android.os.SystemClock.uptimeMillis();
		//System.out.println("now="+now);
		 if (moviestart == 0) {   // first time
             moviestart = now;
             
         }
		 //System.out.println("\tmoviestart="+moviestart);
		 int relTime = (int)((now - moviestart) % movie.duration()) ;
		 //System.out.println("time="+relTime+"\treltime="+movie.duration());
         movie.setTime(relTime);
         movie.draw(canvas,10,10);
         this.invalidate();
	}
}