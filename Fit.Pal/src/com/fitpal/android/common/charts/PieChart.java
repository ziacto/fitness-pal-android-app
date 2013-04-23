package com.fitpal.android.common.charts;

import org.achartengine.ChartFactory;
import org.achartengine.renderer.DefaultRenderer;

import com.fitpal.android.R;
import com.fitpal.android.common.AppInfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class PieChart  extends AbstractDemoChart {
	private int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.CYAN };
	/**
	 * Returns the chart name.
	 * 
	 * @return the chart name
	 */
	public String getName() {
		return "Workout Report : "  + AppInfo.chartUser;
	}

	/**
	 * Returns the chart description.
	 * 
	 * @return the chart description
	 */
	public String getDesc() {
		return "Workout Report for : " + AppInfo.chartUser;
	}

	/**
	 * Executes the chart demo.
	 * 
	 * @param context the context
	 * @return the built intent
	 */
	public Intent execute(Context context) {
		DefaultRenderer renderer = buildCategoryRenderer(generateColor());
		renderer.setZoomButtonsVisible(true);
		renderer.setZoomEnabled(true);
		renderer.setLabelsTextSize(30);
		renderer.setLegendTextSize(40);
		renderer.setChartTitleTextSize(50);
		renderer.setShowLegend(false);
		return ChartFactory.getPieChartIntent(context, buildCategoryDataset("Workout Report", AppInfo.chartKeys, AppInfo.chartValues),
				renderer, "Workout Report");
	}

	private int[] generateColor(){
		
		int requiredSize = AppInfo.chartValues.length;
		System.out.println("generating color : " + requiredSize);
		int newColors[] = new int[requiredSize];
		int colorSize = colors.length;
		
		for(int count = 0; count < requiredSize; count++){
			newColors[count] = colors[count % colorSize];
		}
		
		System.out.println("generate color end");
		return newColors;

	}

}
