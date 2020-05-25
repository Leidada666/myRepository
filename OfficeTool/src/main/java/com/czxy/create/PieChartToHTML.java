package com.czxy.create;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.general.DefaultPieDataset;

import com.lowagie.text.Font;

public class PieChartToHTML {
	/**
	 * 返回图片
	 */
	public void pieOut(HttpServletResponse res,List<List<String>> finalList, String pieTitle) throws Exception {
		//获取pieTitle的index
		int index = finalList.get(0).indexOf(pieTitle);
		//图片标题
		String title = finalList.get(0).get(index) + "成绩总览";
		//统计出成绩
		List<Integer> result = result(finalList, index);
		//图片的数据
		DefaultPieDataset data = getData(result);
		//设置图片字体
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
			//设置标题字体
			standardChartTheme.setExtraLargeFont(new java.awt.Font("隶书", Font.BOLD, 20));
			//应用图例样式
			ChartFactory.setChartTheme(standardChartTheme);
		//生成图片
		JFreeChart chart = ChartFactory.createPieChart3D(title, data, true, false, false);
		//返回给页面
		ServletOutputStream outputStream = null;
		try {
			outputStream = res.getOutputStream();
			ChartUtilities.writeChartAsJPEG(outputStream, 1.0f, chart, 400, 300, null);
		}catch(IOException e) {
			e.printStackTrace();
			throw new Exception();
		}finally {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 统计成绩
	 * nt : 90 - 100
	 * en : 80 - 89
	 * se : 70 - 79
	 * ss : 60 - 69
	 * s  : 小于60
	 */
	private List<Integer> result(List<List<String>> finalList, int index) {
		int nt = 0;			//90-100
		int en = 0;			//80-89
		int se = 0;			//70-79
		int ss = 0;			//60-69
		int s = 0;			//小于60
		List<String> gradeList = null;
		for(int i = 1; i<finalList.size(); i++) {
			gradeList = finalList.get(i);
			String grade = gradeList.get(index);
			
			int x1 = Integer.parseInt(grade);
			if(x1 >= 90 && x1 <= 100) {
				nt++;
			}else if(x1 >= 80 && x1 <= 89) {
				en++;
			}else if(x1 >= 70 && x1 <= 79) {
				se++;
			}else if(x1 >= 60 && x1 <= 69) {
				ss++;
			}else {
				s++;
			}
		}
		//存放各个分数段的人数
		List<Integer> result = new ArrayList<Integer>();
		result.add(nt);
		result.add(en);
		result.add(se);
		result.add(ss);
		result.add(s);
		return result;
	}
	
	//图片数据
	private DefaultPieDataset getData(List<Integer> result) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		Integer index_0 = result.get(0);
		Integer index_1 = result.get(1);
		Integer index_2 = result.get(2);
		Integer index_3 = result.get(3);
		Integer index_4 = result.get(4);
		
		
		dataset.setValue("90-100 : "+index_0, index_0);
		dataset.setValue("80-89 : "+index_1, index_1);
		dataset.setValue("70-79 : "+index_2, index_2);
		dataset.setValue("60-69 : "+index_3, index_3);
		dataset.setValue("<60 : "+index_4, index_4);
		return dataset;
	}
}
