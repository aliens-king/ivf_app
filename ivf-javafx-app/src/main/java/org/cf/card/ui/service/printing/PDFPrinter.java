package org.cf.card.ui.service.printing;

import com.google.common.collect.Lists;
import org.cf.card.ui.service.printing.templates.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PDFPrinter {

	public static void main(String[] args) {
		new PDFPrinter(null);
	}

	public PDFPrinter(List<Component> comp) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				printComponent(comp);
			}
		});
	}

	public class TestPane extends JPanel {

		private BufferedImage bg;

		public TestPane() {
			try {
				bg = ImageIO.read(new File("path/to/a/image"));
			} catch (IOException ex) {
				Logger.getLogger(PDFPrinter.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		@Override
		public Dimension getPreferredSize() {
			return bg == null ? new Dimension(200, 200) : new Dimension(bg.getWidth(), bg.getHeight());
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			if (bg != null) {
				int x = (getWidth() - bg.getWidth()) / 2;
				int y = (getHeight() - bg.getHeight()) / 2;
				g2d.drawImage(bg, x, y, this);
			}
			g2d.dispose();
		}
	}

	public void printComponent(List<Component> comp) {
		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setJobName(" Print Component ");

		List<Component> braceletTemplate = new ArrayList<>();
		List<Component> fileTemplate = new ArrayList<>();
		List<Component> fMDishTemplate = new ArrayList<>();
		List<Component> specialPotTemplate = new ArrayList<Component>();
		List<Component> strawTemplate = new ArrayList<>();
		int j = 0;
		int k = 0;
		int l = 0;
		int m = 0;
		// separate the mixed component list into lists of the same kind
		// templates
		for (int i = 0; i < comp.size(); i++) {
			if (comp.get(i) instanceof org.cf.card.ui.service.printing.templates.braceletTemplate) {
				k++;
				braceletTemplate.add(comp.get(i));
//				if (k % 6 == 0) {
//					JPanel blank5 = new JPanel();
//					JPanel blank6 = new JPanel();
//					JPanel blank7 = new JPanel();
//					blank5.setBackground(Color.white);
//					blank6.setBackground(Color.white);
//					blank7.setBackground(Color.white);
//					braceletTemplate.add(blank5);
//					braceletTemplate.add(blank6);
//					braceletTemplate.add(blank7);
//				}
			}
			if (comp.get(i) instanceof org.cf.card.ui.service.printing.templates.fileTemplate)
				fileTemplate.add(comp.get(i));
			if (comp.get(i) instanceof FMDish) {
				j++;
				fMDishTemplate.add(comp.get(i));
				// if (j % 16 == 0) {
				// JPanel blank1 = new JPanel();
				// JPanel blank2 = new JPanel();
				//
				// blank1.setBackground(Color.white);
				// blank2.setBackground(Color.white);
				//
				// fMDishTemplate.add(blank1);
				// fMDishTemplate.add(blank2);
				//
				//
				// }
			}
			if (comp.get(i) instanceof SpecialPot) {
				specialPotTemplate.add(comp.get(i));
//				JPanel blank10 = new JPanel();
//				JPanel blank11 = new JPanel();
//				blank10.setBackground(Color.white);
//				blank11.setBackground(Color.white);
//				specialPotTemplate.add(blank10);
//				specialPotTemplate.add(blank11);
			}
			if (comp.get(i) instanceof Straw) {
				l++;
				strawTemplate.add(comp.get(i));
				// if(l % 3 == 0){
				// JPanel blank9 = new JPanel();
				// blank9.setBackground(Color.white);
				// strawTemplate.add(blank9);
				// }
			}

		}

		List<List<Component>> partitionsFileTemplate = Lists.partition(fileTemplate, 21);
		List<List<Component>> partitionsBraceletTemplate = Lists.partition(braceletTemplate, 108);
		List<List<Component>> partitionsFMDishTemplate = Lists.partition(fMDishTemplate, 240);
		List<List<Component>> partitionsSpecialPotTemplate = Lists.partition(specialPotTemplate, 36);
		List<List<Component>> partitionsStrawTemplate = Lists.partition(strawTemplate, 48);

		Book book = new Book();

//		double width = 595.276;
//		double height = 841.90;

		double width = javafx.print.Paper.A4.getWidth();
		double height = javafx.print.Paper.A4.getHeight();

		System.out.println("------width---"+width);
		System.out.println("------height---"+height);
//
//		Paper p = new Paper();
//		PageFormat page = new PageFormat();
//		p.setSize(width, height);
		double x = 0;
		double y = 0;
		// p.setImageableArea(x, y, p.getWidth() - x , p.getHeight()
		// - y * 2);
		// page.setPaper(p);
		// The program doesen't print empty pages
		if (partitionsBraceletTemplate.size() != 0) {
			x = 14.17;
			y = 45.35;
			Paper p = new Paper();
			PageFormat page = new PageFormat();
			p.setSize(width, height);
			p.setImageableArea(x, y, p.getWidth() - x * 2, p.getHeight() - y * 2);
			page.setPaper(p);
			for (int i = 0; i < partitionsBraceletTemplate.size(); i++) {
				book.append(new ComponentPrintable(partitionsBraceletTemplate.get(i), false), page);
			}
		}

		if (partitionsFileTemplate.size() != 0) {
			x = 0.01;
			y = 14.17;
			Paper p = new Paper();
			PageFormat page = new PageFormat();
			p.setSize(width, height);
			p.setImageableArea(x, y, p.getWidth(), p.getHeight() - y * 2);
			page.setPaper(p);
			for (int i = 0; i < partitionsFileTemplate.size(); i++) {
				book.append(new ComponentPrintable(partitionsFileTemplate.get(i), false), page);
			}
		}

		if (partitionsFMDishTemplate.size() != 0) {
			x = 22.67;
			y = 32.59;
			Paper p = new Paper();
			PageFormat page = new PageFormat();
			p.setSize(width, height);
			p.setImageableArea(x, y, p.getWidth() - x, p.getHeight() - y - 35.43);
			page.setPaper(p);
			for (int i = 0; i < partitionsFMDishTemplate.size(); i++) {
				book.append(new ComponentPrintable(partitionsFMDishTemplate.get(i), false), page);
			}
		}

		if (partitionsSpecialPotTemplate.size() != 0) {
			x = 14.17;
			y = 28.34;
			Paper p = new Paper();
			PageFormat page = new PageFormat();
			p.setSize(width, height);
			p.setImageableArea(x, y, p.getWidth() - x - 13.33, p.getHeight() - y * 2);
			page.setPaper(p);
			for (int i = 0; i < partitionsSpecialPotTemplate.size(); i++) {
				book.append(new ComponentPrintable(partitionsSpecialPotTemplate.get(i), false), page);
			}
		}

		if (partitionsStrawTemplate.size() != 0) {
			x = 42.23;
			y = 14.17;
			Paper p = new Paper();
			PageFormat page = new PageFormat();
			p.setSize(width, height);
			p.setImageableArea(x, y, p.getWidth() - x * 2, p.getHeight() - y - 11.33);
			page.setPaper(p);
			for (int i = 0; i < partitionsStrawTemplate.size(); i++) {
				book.append(new ComponentPrintable(partitionsStrawTemplate.get(i), false), page);
			}
		}

		pj.setPageable(book);

		if (!pj.printDialog()) {
			return;
		}
		try {
			pj.print();
		} catch (PrinterException ex) {
			System.out.println(ex);
		}
	}

	public class ComponentPrintable implements Printable {

		private Component comp;
		boolean mirror = false;

		private ComponentPrintable(List<Component> comp, boolean mirror) {
			this.comp = multiCardPannel(comp);
			this.mirror = mirror;

		}

		private JPanel multiCardPannel(List<Component> comp) {

			GridLayout multicard = new GridLayout();

			if (comp.get(0) instanceof FMDish) {
				multicard.setColumns(15);
				multicard.setRows(0);
				multicard.setHgap(0);
				multicard.setVgap(0);
			} else if (comp.get(0) instanceof fileTemplate) {
				multicard.setColumns(3);
				multicard.setRows(0);
				multicard.setHgap(0);
				multicard.setVgap(0);
			} else if (comp.get(0) instanceof SpecialPot) {
				multicard.setColumns(4);
				multicard.setRows(0);
				multicard.setHgap(0);
				multicard.setVgap(0);
			} else if (comp.get(0) instanceof braceletTemplate) {
				multicard.setColumns(6);
				multicard.setRows(0);
				multicard.setHgap(0);
				multicard.setVgap(0);
			} else if (comp.get(0) instanceof Straw) {
				multicard.setColumns(4);
				multicard.setRows(0);
				multicard.setHgap(0);
				multicard.setVgap(0);
			}

			final JPanel printablePanel = new JPanel();
			printablePanel.setBackground(Color.WHITE);
			JScrollPane scr = new JScrollPane(printablePanel);
			printablePanel.setLayout(multicard);
			JPanel blank = new JPanel();
			blank.setBackground(Color.WHITE);

			// Add templates to pannel
			if (mirror == true)
				for (int i = 0; i < comp.size(); i++) {
					if (comp.get(i) instanceof FMDish) {
						if (i % 16 == 0) {
							System.out.println("x");
							printablePanel.add(blank);
							printablePanel.add(blank);
							printablePanel.add(blank);
							printablePanel.add(blank);
						} else
							printablePanel.add(comp.get(i));
					} else
						printablePanel.add(comp.get(i));
				}
			else
				for (int i = 0; i < comp.size(); i++) {

					printablePanel.add(comp.get(i));
				}

			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// Set up the content pane.
			frame.add(scr);
			frame.repaint();

			// Display the window.

			frame.setVisible(true);
			frame.setVisible(false);

			return printablePanel;

		}

		@Override
		public int print(Graphics g, PageFormat pf, int pageNumber) throws PrinterException {
			// TODO Auto-generated method stub
			// if (pageNumber > 0) {
			// return Printable.NO_SUCH_PAGE;
			// }

			// Get the preferred size ofthe component...
			Dimension compSize = comp.getPreferredSize();
			// Make sure we size to the preferred size
			comp.setSize(compSize);
			// Get the the print size
			Dimension printSize = new Dimension();
			printSize.setSize(pf.getImageableWidth(), pf.getImageableHeight());

			// Calculate the scale factor
			double scaleFactor = 0.29151;
			// Don't want to scale up, only want to scale down
			if (scaleFactor > 1d) {
				scaleFactor = 1d;
			}

			// Calcaulte the scaled size...
			double scaleWidth = compSize.width * scaleFactor;
			double scaleHeight = compSize.height * scaleFactor;

			// Create a clone of the graphics context. This allows us to
			// manipulate
			// the graphics context without begin worried about what effects
			// it might have once we're finished
			Graphics2D g2 = (Graphics2D) g.create();
			// Calculate the x/y position of the component, this will center
			// the result on the page if it can
			double x = ((pf.getImageableWidth() - scaleWidth) / 2d) + pf.getImageableX();
			double y = ((pf.getImageableHeight() - scaleHeight) / 2d) + pf.getImageableY();
			// Create a new AffineTransformation
			AffineTransform at = new AffineTransform();
			// Translate the offset to out "center" of page

			System.out.println("Imageable---x=" + pf.getImageableX() + "   y=" + pf.getImageableY());

			at.translate(pf.getImageableX(), pf.getImageableY());
			// Set the scaling
			at.scale(scaleFactor, scaleFactor);
			// Apply the transformation

			g2.transform(at);
			// Print the component
			comp.printAll(g2);
			// Dispose of the graphics context, freeing up memory and discarding
			// our changes
			g2.dispose();

			// comp.revalidate();
			return Printable.PAGE_EXISTS;
		}
	}

}