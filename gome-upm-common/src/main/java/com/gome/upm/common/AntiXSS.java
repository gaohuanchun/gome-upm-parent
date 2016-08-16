package com.gome.upm.common;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Substitution;
import org.apache.oro.text.regex.Util;


public class AntiXSS {
	static final Pattern SCRIPT_TAG_PATTERN = Pattern.compile(
	"<script[^>]*>.*</script[^>]*>", Pattern.CASE_INSENSITIVE);
	
	static final Pattern IMG_TAG_PATTERN = Pattern.compile("<img[^>]*>.*</img[*>]*>",Pattern.CASE_INSENSITIVE);
    static final String XSS_CONTENT = "";
	public static String antiXSS(String content) {
	    content = content.trim();
		content = content.replaceAll(
				"%0",XSS_CONTENT).replaceAll(
						"%[fF]{2}",XSS_CONTENT).replaceAll(
								"%[20|22|27]{2}",XSS_CONTENT).replaceAll(
										"%3[eE|cC|bB]", XSS_CONTENT);
		
		String old = content;
		String ret = _antiXSS(content);
		while (!ret.equals(old)) {
		old = ret;
		ret = _antiXSS(ret);
		}

		Pattern p = Pattern.compile("[/<>?\"\\\\|*:;]");
		Matcher m = p.matcher(ret);
		ret = m.replaceAll(XSS_CONTENT);
		
		return ret;
	}

	private static String _antiXSS(String content) {
		try {
		return stripAllowScriptAccess(stripSpecialLetter(stripSpecial(stripProtocol(stripCssExpression(stripAsciiAndHex(stripEvent(stripImgTag(stripScriptTag(content)))))))));
		} catch (Exception e) {
		e.printStackTrace();
		return null;
		}
	}

	private static String stripScriptTag(String content) {
		Matcher m = SCRIPT_TAG_PATTERN.matcher(content);
		content = m.replaceAll(XSS_CONTENT);
		return content;
	}
	
	private static String stripImgTag(String content){
		Matcher m = IMG_TAG_PATTERN.matcher(content);
		content = m.replaceAll(XSS_CONTENT);
		return content;
	}

	private static String stripEvent(String content) throws Exception {
		PatternCompiler pc = new Perl5Compiler();
		PatternMatcher matcher = new Perl5Matcher();
		String[] events = { "onmouseover", "onmouseout", "onmousedown",
			"onmouseup", "onmousemove", "onclick", "ondblclick",
			"onkeypress", "onkeydown", "onkeyup", "ondragstart",
			"onerrorupdate", "onhelp", "onreadystatechange", "onrowenter",
			"onrowexit", "onselectstart", "onload", "onunload",
			"onbeforeunload", "onblur", "onerror", "onfocus", "onresize",
			"onscroll", "oncontextmenu" };
			for (String event : events) {
			org.apache.oro.text.regex.Pattern p = pc.compile("(<[^>]*)("
			+ event + ")([^>]*>)", Perl5Compiler.CASE_INSENSITIVE_MASK);
			if (null != p)
			content = Util.substitute(matcher, p, new Perl5Substitution(
			"$1" + event.substring(2) + "$3"), content,
			Util.SUBSTITUTE_ALL);
	
		}
			return content;
	}

	private static String stripAsciiAndHex(String content) throws Exception {
		PatternCompiler pc = new Perl5Compiler();
		PatternMatcher matcher = new Perl5Matcher();
		// filter &# \00xx
		org.apache.oro.text.regex.Pattern p = pc.compile(
		"(<[^>]*)(&#|\\\\00)([^>]*>)",
		Perl5Compiler.CASE_INSENSITIVE_MASK);
			if (null != p)
			content = Util
			.substitute(matcher, p, new Perl5Substitution("$1$3"),
			content, Util.SUBSTITUTE_ALL);
		return content;
	}
	private static String stripSpecial(String content)throws Exception{
		PatternCompiler pc = new Perl5Compiler();
		PatternMatcher matcher = new Perl5Matcher();
		
		org.apache.oro.text.regex.Pattern p = pc.compile(
				"(&)([lt|gt|amp|quot|nbsp]{2,5})(;?)",Perl5Compiler.CASE_INSENSITIVE_MASK);
				
		if(null != p){
			content = Util.substitute(matcher, p, new Perl5Substitution("$2"),
					content,Util.SUBSTITUTE_ALL);
		}
		
		return content;
		
	}
	
	private static String stripSpecialLetter(String content)throws Exception{
		PatternCompiler pc = new Perl5Compiler();
		PatternMatcher matcher = new Perl5Matcher();
		org.apache.oro.text.regex.Pattern p = pc.compile(
				"(&#)(.*)(;?)",Perl5Compiler.CASE_INSENSITIVE_MASK);
				
		if(null != p){
			content = Util.substitute(matcher, p, new Perl5Substitution("$2"),
					content,Util.SUBSTITUTE_ALL);
		}
		
		return content;
		
	}

	private static String stripCssExpression(String content) throws Exception {
		PatternCompiler pc = new Perl5Compiler();
		PatternMatcher matcher = new Perl5Matcher();
		org.apache.oro.text.regex.Pattern p = pc.compile(
		"(<[^>]*style=.*)/\\*.*\\*/([^>]*>)",
		Perl5Compiler.CASE_INSENSITIVE_MASK);
		if (null != p)
		content = Util
		.substitute(matcher, p, new Perl5Substitution("$1$2"),
		content, Util.SUBSTITUTE_ALL);
	
		p = pc
		.compile(
		"(<[^>]*style=[^>]+)(expression|javascript|vbscript|-moz-binding)([^>]*>)",
		Perl5Compiler.CASE_INSENSITIVE_MASK);
		if (null != p)
		content = Util
		.substitute(matcher, p, new Perl5Substitution("$1$3"),
		content, Util.SUBSTITUTE_ALL);
	
		p = pc.compile("(<style[^>]*>.*)/\\*.*\\*/(.*</style[^>]*>)",
		Perl5Compiler.CASE_INSENSITIVE_MASK);
		if (null != p)
		content = Util
		.substitute(matcher, p, new Perl5Substitution("$1$2"),
		content, Util.SUBSTITUTE_ALL);
	
		p = pc
		.compile(
		"(<style[^>]*>[^>]+)(expression|javascript|vbscript|-moz-binding)(.*</style[^>]*>)",
		Perl5Compiler.CASE_INSENSITIVE_MASK);
		if (null != p)
		content = Util
		.substitute(matcher, p, new Perl5Substitution("$1$3"),
		content, Util.SUBSTITUTE_ALL);
		return content;
		}
	
		private static String stripProtocol(String content) throws Exception {
			PatternCompiler pc = new Perl5Compiler();
			PatternMatcher matcher = new Perl5Matcher();
			String[] protocols = { "javascript", "vbscript", "livescript",
			"ms-its", "mhtml", "data", "firefoxurl", "mocha" };
			for (String protocol : protocols) {
			org.apache.oro.text.regex.Pattern p = pc.compile("(<[^>]*)"
			+ protocol + ":([^>]*>)",
			Perl5Compiler.CASE_INSENSITIVE_MASK);
			if (null != p)
			content = Util.substitute(matcher, p, new Perl5Substitution(
			"$1/$2"), content, Util.SUBSTITUTE_ALL);
		}
		return content;
	}

	private static synchronized String stripAllowScriptAccess(String content)
	throws Exception {
		PatternCompiler pc = new Perl5Compiler();
		PatternMatcher matcher = new Perl5Matcher();
		org.apache.oro.text.regex.Pattern p = pc.compile(
		"(<[^>]*)AllowScriptAccess([^>]*>)",
		Perl5Compiler.CASE_INSENSITIVE_MASK);
		if (null != p)
		content = Util.substitute(matcher, p, new Perl5Substitution(
		"$1Allow_Script_Access$2"), content, Util.SUBSTITUTE_ALL);
		return content;
	}
	
    /**
     * 验证是否有非法字符 / < > ? \ " \ | * ; :以及制表符等看不见的符号
     */
    public static  boolean isXssCode(String ...params) {
        for (String param : params) {
            boolean flag=Pattern.matches(".*[/<>?\"\t\r\n\b\f\\\\|*:;].*", param);
             if(flag){
                 return true;
             }
        }
        return false;
    }
	
	public static void main(String[] args) {
        System.out.println( AntiXSS.antiXSS("xxx  ")+"///");
    }
	
	
	
} 
