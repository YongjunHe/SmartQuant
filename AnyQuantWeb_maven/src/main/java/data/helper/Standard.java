package data.helper;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

public class Standard {
	public static final String CCIStandard = "CCI指标的运行区间可分为三大类：<br>" + 
			"1.大于＋100、小于－100和＋100—－100之间;<br>"+
			"2.当CCI＞＋100时，表明股价已经进入非常态区间——超买区间，股价的异动现象应多加关注;<br>" + 
			"3.当CCI＜－100时，表明股价已经进入另一个非常态区间——超卖区间，投资者可以逢低吸纳股票;<br>"+
			"4.当CCI介于＋100—－100之间时表明股价处于窄幅振荡整理的区间——常态区间，投资者应以观望为主.<br>";
	public static final String RSIStandard = "RSI的变动范围在0—100之间，强弱指标值一般分布在20—80：<br>" + 
			"1.80—100极强卖出;<br>"+
			"2.50—80强买入;<br>"+
			"3.20—50弱观望;<br>"+
			"4.0—20极弱买入.<br>";

	public static final String WMSRStandard = "威廉指数的变动范围在0—100之间：<br>" + 
			"1.当威廉指数线高于85，市场处于超卖状态，行情即将见底；<br>" + 
			"2.当威廉指数线低于15，市场处于超买状态，行情即将见顶；<br>" + 
			"3.介于之间，股市行情稳定，持观望.<br>";

	public static final String KDJStandard = "KDJ的使用要领：<br>" + 
			"1.KDJ指标的区间主要分为3个小部分，即20以下、20—80之间和80以上.<br>" + 
			"其中20以下的区间为超买区；80以上的区域为超卖区；20—80之间的区域为买卖平衡区；<br>" + 
			"2.如果K、D、J值都大于50时，为多头市场，后市看涨；如果K、D、J值都小于50时，为空头市场，后市看空；<br>" + 
			"3.KDJ指标图形中，D曲线运行速度最慢，敏感度最低；其次是K曲线，J曲线敏感度最强；<br>" +
			"4.当J大于K、K大于D时，即3条指标曲线呈多头排列，显示当前为多头市场；当3条指标出现黄金交叉时，指标发出买入信号；<br>" + 
			"5.当3条指标曲线呈空头排列时，表示短期是下跌趋势；3条曲线出现死亡交叉时，指标发出卖出信号；<br>" + 
			"6.如果KD线交叉突破反复在50左右震荡，说明行情正在整理，此时要结合J值，观察KD偏离的动态，再决定投资行动.<br>";
	
	public static final String ATRStandard = "均幅指标是显示市场变化率的指标，这个指标来进行预测的原则可以表达为: <br>"
			+ "该指标价值越高，趋势改变的可能性就越高;<br>"
			+ "该指标的价值越低，趋势的移动性就越弱.<br>";



			public static void main(String [] args){

		System.out.println(CCIStandard);
		System.out.println(RSIStandard);
		System.out.println(WMSRStandard);
		System.out.println(ATRStandard);

	}

}
