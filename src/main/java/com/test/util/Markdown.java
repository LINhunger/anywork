package com.test.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

/**
 * Created by hunger on 2016/11/5.
 */
public class Markdown {

    private final static PegDownProcessor md = new PegDownProcessor(Extensions.ALL_WITH_OPTIONALS);

    private static String addStyle(String html) {
        Document doc = Jsoup.parseBodyFragment(html);
        Element body = doc.body();
        Elements imgs = body.getElementsByTag("img");
        for (Element e :imgs) {
            String src = e.attr("src").replaceAll("https","http");
            e.attr("src",src);
        }
        imgs.attr("style","max-width:100%;");
        return body.toString().replaceAll("<body>|</body>","");
    }
    public static String pegDown(String content) {
        String html = md.markdownToHtml(content);
        return addStyle(html);
    }












    public static void main(String[] args){
        String text = "asddddd\n" +
                "\n" +
                "`asddsadasdas`\n" +
                "\n" +
                "```\n" +
                "fdsfsdf\n" +
                "sfdfdsfsdf\n" +
                "```";
        System.out.println(pegDown(text));
    }
}
