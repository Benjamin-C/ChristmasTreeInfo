package https;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLServerSocketFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import https.HTTPServer.ContextHandler;
import https.HTTPServer.FileContextHandler;
import https.HTTPServer.Request;
import https.HTTPServer.Response;
import https.HTTPServer.VirtualHost;

public class HTTPSCustomerFactory {

	//private static int init;
	
	public static void main(String argsalt[]) {
		System.setProperty("javax.net.ssl.trustStore","/home/benjamin/mykey.keystore");
		final String args[] = new String[2];
		args[0] = "/home/benjamin";
		args[1] = "9000";
		System.out.println("Starting here");
//        SimpleHTTPSServer server = new SimpleHTTPSServer();
        Thread t = new Thread() {
        	@Override public void run() { 
        		try {
                    if (args.length == 0) {
                        System.err.printf("Usage: java [-options] %s <directory> [port]%n" +
                            "To enable SSL: specify options -Djavax.net.ssl.keyStore, " +
                            "-Djavax.net.ssl.keyStorePassword, etc.%n", HTTPServer.class.getName());
                        return;
                    }
                    File dir = new File(args[0]);
                    if (!dir.canRead())
                        throw new FileNotFoundException(dir.getAbsolutePath());
                    int port = args.length < 2 ? 80 : Integer.parseInt(args[1]);
                    // set up server
                    for (File f : Arrays.asList(new File("/etc/mime.types"), new File(dir, ".mime.types")))
                        if (f.exists())
                            HTTPServer.addContentTypes(f);
                    HTTPServer server = new HTTPServer(port);
                    if (System.getProperty("javax.net.ssl.keyStore") != null) // enable SSL if configured
                        server.setServerSocketFactory(SSLServerSocketFactory.getDefault());
                    VirtualHost host = server.getVirtualHost(null); // default host
                    host.setAllowGeneratedIndex(true); // with directory index pages
                    host.addContext("/", new FileContextHandler(dir));
                    host.addContext("/api/time", new ContextHandler() {
                        public int serve(Request req, Response resp) throws IOException {
                            long now = System.currentTimeMillis();
                            resp.getHeaders().add("Content-Type", "text/plain");
                            resp.send(200, String.format("%tF %<tT", now));
                            return 0;
                        }
                    });
                    server.start();
                    System.out.println("HTTPServer is listening on port " + port);
                } catch (Exception e) {
                    System.err.println("error: " + e);
                } }};
        t.start();

//				SimpleHTTPSServer3 server = new SimpleHTTPSServer3();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} } };
//        System.out.println("Running");
    	
//		try {
//			SimpleHTTPSServer2 sv = new SimpleHTTPSServer2();
//		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException | CertificateException
//				| IOException | KeyManagementException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//connect(0);
        System.out.println("Ran");
        JFrame jf = new JFrame("HTTPS Server");
        JPanel jp = new JPanel();
        String pattern = "yyyy-MM-dd hh:mm:ss.SSS";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        JLabel jl = new JLabel("Started at " + simpleDateFormat.format(new Date(System.currentTimeMillis())));
        JButton jb = new JButton("End Server");
        jb.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) { System.exit(0); } });
        jp.add(jl);
        jp.add(jb);
        jf.add(jp);
        jf.pack();
        jf.validate();
        jf.setVisible(true);
		Scanner s = new Scanner(System.in);
		System.out.println("Hit enter to end");
		while(!s.hasNext());
		System.out.println("Goodby");
		s.close();
		System.exit(0);
	}
	public static void connect(int ip) {
		String https_url = "https://172.217.7.238:80";
		URL url;
		try {
			url = new URL(https_url);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			
			//dumpl all cert info
			print_https_cert(con);
				
			//dump all the content
			print_content(con);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private static void print_https_cert(HttpsURLConnection con) {
		if(con!=null){	
			try {	
				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");	
				Certificate[] certs = con.getServerCertificates();
				for(Certificate cert : certs){
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : " 
							+ cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : " 
							+ cert.getPublicKey().getFormat());
					System.out.println("\n");
				}		
			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	private static void print_content(HttpsURLConnection con) {
		if(con!=null){
			try {
				System.out.println("****** Content of the URL ********");			
				BufferedReader br = 
				new BufferedReader(
					new InputStreamReader(con.getInputStream()));	
				String input;	
				while ((input = br.readLine()) != null){
					System.out.println(input);
				}
				br.close();	
			} catch (IOException e) {
			   e.printStackTrace();
			}
		}		
	}
}
