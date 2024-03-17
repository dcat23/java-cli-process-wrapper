package pro.macchiato;

import pro.macchiato.cli.TransCli;
import pro.macchiato.cli.exceptions.CliException;


public class Main {

    public static void main(String[] args) throws CliException {

        TransCli trans = new TransCli();
        trans.addOption("https://get.freecoursesonline.me/wp-content/uploads/2024/02/freecoursesonline.me-linkedin-full-stack-web-applications-with-rust-and-leptos.torrent");

    }
}