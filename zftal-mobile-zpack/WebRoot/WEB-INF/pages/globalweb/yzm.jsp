<%@ page contentType="image/jpeg"
         import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*,com.sun.image.codec.jpeg.JPEGCodec,com.sun.image.codec.jpeg.JPEGImageEncoder" %>
<%@ page contentType="image/jpeg" import="java.io.OutputStream" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    int width = 65, height = 19;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics g = image.getGraphics();
    OutputStream os = response.getOutputStream();
    Random random = new Random();
    g.setColor(new Color(255, 255, 255));
    g.fillRect(0, 0, width, height);
    g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
    g.setColor(new Color(255, 255, 255));//--del
    for (int i = 0; i < 40; i++) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(12);
        int yl = random.nextInt(3);
        //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
        g.setColor(new Color(255, 255, 255));//--del
        g.drawLine(x, y, x + xl, y + yl);
    }
    String sRand = "";
    for (int i = 0; i < 4; i++) {
        int j = (random.nextInt(35) + 48);
        if (j > 57) {
            j += 8;
        }
        if (j == 48) {
            j += random.nextInt(7) + 1;
        }
        if (j == 49) {
            j += random.nextInt(7) + 1;
        }
        if (j == 73) {
            j += random.nextInt(7) + 1;
        }
        if (j == 79) {
            j += random.nextInt(7) + 1;
        }
        if (j == 111) {
            j += random.nextInt(7) + 1;
        }

        String rand = String.valueOf((char) j);
        sRand += rand;
        g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
        g.drawString(rand, 13 * i + 6, 16);
    }
    session.setAttribute("yzm", sRand);
    g.dispose();
    out.clear();
    request.setAttribute("gzip", "false");
//ImageIO.write(image, "JPEG", response.getOutputStream());
    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
    encoder.encode(image);
//ImageIO.write(image,"JPEG",os);
    os.flush();
    os.close();
    os = null;
    out.clear();
    out = pageContext.pushBody();
//response.flushBuffer();
//
//out = pageContext.pushBody();  
//return;  
%>
