package Theme;

public class Theme {

    public static final String background = "arc:20;background:" + Paleta.getbColorFondo();
    public static final String panel = "arc:30;background:" + Paleta.getbColorPanel();
    public static final String Headerpanel = "arc:30;background:lighten(" + Paleta.getbColorPanel()+",10%)";
    public static final String h1 = "font:$h1.font;foreground:" + Paleta.getbColortextPrincipal();
    public static final String h2 = "font:$h2.font;foreground:" + Paleta.getbColortextPrincipal();
    public static final String h3 = "font:$h3.font;foreground:" + Paleta.getbColortextPrincipal();
    public static final String s1 = "font:$h3.font;foreground:" + Paleta.getBColortextSegundario();
    public static final String btnPrinci = "arc:20;background:" + Paleta.getbAcent1()+ ";borderWidth:0;focusWidth:0;"+"foreground:"+ Paleta.getbColortextPrincipal();
    public static final String btnSecun = "arc:20;background:" + Paleta.getbAcent2()+ ";borderWidth:0;focusWidth:0;"+"foreground:"+ Paleta.getbColortextPrincipal();
    public static final String btnTer = "arc:20;background:" + Paleta.getbAcent3()+ ";borderWidth:0;focusWidth:0;"+"foreground:"+ Paleta.getbColortextPrincipal();
     public static final String selectMenu = panel+";borderColor:"+Paleta.getbColorPanel()+";"+h3;
    public static final String errorField = "borderColor:#E74C3C;focusedBorderColor:#E74C3C";
    public static final String normalField = "";
    public static String getBackground() {
        return background;
    }

    public static String getPanel() {
        return panel;
    }

    public static String getH1() {
        return h1;
    }

    public static String getH2() {
        return h2;
    }

    public static String getH3() {
        return h3;
    }

    public static String getS1() {
        return s1;
    }

    public static String getBtnPrinci() {
        return btnPrinci;
    }

    public static String getHeaderpanel() {
        return Headerpanel;
    }

    public static String getBtnSecun() {
        return btnSecun;
    }

    public static String getBtnTer() {
        return btnTer;
    }

    public static String getSelectMenu() {
        return selectMenu;
    }

    public static String getErrorField() {
        return errorField;
    }

    public static String getNormalField() {
        return normalField;
    }
    

}
