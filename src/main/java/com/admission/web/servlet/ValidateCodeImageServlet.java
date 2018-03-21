package com.admission.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admission.web.config.WebProfile;

/**
 * Servlet implementation class ValidateCodeImageServlet
 */
public class ValidateCodeImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String[] CODE_ELEMENTS = new String[] {
		"A", "B", "C", "D", "E", "F", "G", "H", "K", "M", "N", "P", "Q", "R", "S", "T", "W", "X", "Y",
		"2", "3", "4", "5", "6", "7", "8", "9"
	};
	private static final int IMG_WIDTH = 80;
	private static final int IMG_HEIGHT = 40;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValidateCodeImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = getRandomCode();
		request.getSession().setAttribute(WebProfile.SESSION_VALIDATECODE, code);
		
		BufferedImage buf = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = buf.getGraphics();
		g.setColor(new Color(195, 195, 195));
		g.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
		g.setColor(new Color(136, 0, 21));
		g.setFont(new Font("楷体", Font.BOLD, 26));
		FontMetrics fm = g.getFontMetrics();
		int fh = fm.getHeight();
		int fw = fm.stringWidth(code);
		
		int x = (IMG_WIDTH - fw) / 2;
		int y = (IMG_HEIGHT - fh) / 2 + fh*4/5;
		
		g.drawString(code, x, y);
		
		response.setContentType("image/jpeg");
		ImageIO.write(buf, "jpeg", response.getOutputStream());
		response.flushBuffer();
	}
	
	private String getRandomCode() {
		StringBuffer buf = new StringBuffer();
		for(int i=0; i<5; i++) {
			int pos = (int)(CODE_ELEMENTS.length * Math.random());
			buf.append(CODE_ELEMENTS[pos]);
		}
		
		return buf.toString();
	}

}
