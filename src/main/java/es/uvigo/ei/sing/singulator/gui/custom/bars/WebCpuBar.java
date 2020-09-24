package es.uvigo.ei.sing.singulator.gui.custom.bars;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.alee.extended.statusbar.MemoryBarListener;
import com.alee.extended.statusbar.WebMemoryBarStyle;
import com.alee.global.StyleConstants;
import com.alee.laf.label.WebLabel;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.language.LanguageManager;
import com.alee.managers.tooltip.TooltipManager;
import com.alee.managers.tooltip.WebCustomTooltip;
import com.alee.utils.CollectionUtils;
import com.alee.utils.GraphicsUtils;
import com.alee.utils.LafUtils;
import com.alee.utils.SizeUtils;
import com.alee.utils.laf.ShapeProvider;
import com.alee.utils.swing.ComponentUpdater;
import com.alee.utils.swing.SizeMethods;
import com.sun.management.OperatingSystemMXBean;

import es.uvigo.ei.sing.singulator.gui.constant.Constants;
import es.uvigo.ei.sing.singulator.gui.constant.Functions;

@SuppressWarnings("restriction")
public class WebCpuBar extends WebLabel implements ShapeProvider, SizeMethods<WebLabel> {
	private static final long serialVersionUID = 1L;

	public static final String THREAD_NAME = "WebCpuBar.updater";

	private ImageIcon cpuIcon = Functions.loadIcon(Constants.ICON_CPU_16);
	private Color allocatedBorderColor = WebMemoryBarStyle.allocatedBorderColor;
	private Color allocatedDisabledBorderColor = WebMemoryBarStyle.allocatedDisabledBorderColor;
	private Color minimumFillColor = Color.decode(Constants.COLOR_GREEN);
	private Color minimumBorderColor = Color.decode(Constants.COLOR_GREEN);
	private Color highFillColor = Color.decode(Constants.COLOR_ORANGE);
	private Color highBorderColor = Color.decode(Constants.COLOR_ORANGE);

	public boolean drawBorder = WebMemoryBarStyle.drawBorder;
	public boolean fillBackground = WebMemoryBarStyle.fillBackground;
	private int leftRightSpacing = WebMemoryBarStyle.leftRightSpacing;
	private int shadeWidth = WebMemoryBarStyle.shadeWidth;
	private int round = WebMemoryBarStyle.round;

	private boolean allowGcAction = WebMemoryBarStyle.allowGcAction;

	private boolean showTooltip = WebMemoryBarStyle.showTooltip;
	private int tooltipDelay = WebMemoryBarStyle.tooltipDelay;

	private boolean showMaximumCPU = WebMemoryBarStyle.showMaximum;

	private final List<MemoryBarListener> listeners = new ArrayList<MemoryBarListener>(1);

	private long usedLocalCPU = 0;
	private long usedSystemCPU = 0;
	private long maxCPU = 100;

	private int refreshRate = 1000;
	private ComponentUpdater updater = null;

	private boolean pressed = false;

	private WebCustomTooltip tooltip;
	private final WebLabel tooltipLabel;

	public WebCpuBar() {
		super();

		setOpaque(false);
		setFocusable(true);
		setHorizontalAlignment(SwingConstants.CENTER);

		updateBorder();

		tooltipLabel = new WebLabel(cpuIcon);
		tooltipLabel.setStyleId("memory-bar-tip");
		updateTooltip();

		updateCPU();

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if (isEnabled()) {
					if (Hotkey.SPACE.isTriggered(e) || Hotkey.ENTER.isTriggered(e)) {
						pressed = true;
						repaint();
					}
				}
			}

			@Override
			public void keyReleased(final KeyEvent e) {
				if (Hotkey.SPACE.isTriggered(e) || Hotkey.ENTER.isTriggered(e)) {
					pressed = false;
					if (isEnabled()) {
						doGC();
					} else {
						repaint();
					}
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				if (allowGcAction && isEnabled() && SwingUtilities.isLeftMouseButton(e)) {
					pressed = true;
					requestFocusInWindow();
					doGC();
				}
			}

			@Override
			public void mouseReleased(final MouseEvent e) {
				if (pressed && SwingUtilities.isLeftMouseButton(e)) {
					pressed = false;
					repaint();
				}
			}
		});

		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(final FocusEvent e) {
				repaint();
			}

			@Override
			public void focusLost(final FocusEvent e) {
				repaint();
			}
		});

		// Values updater
		updater = ComponentUpdater.install(this, THREAD_NAME, refreshRate, new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				updateCPU();
			}
		});
	}

	public void doGC() {
		fireGcCalled();
		System.gc();
		updateCPU();
		fireGcCompleted();
	}

	private void updateBorder() {
		if (drawBorder) {
			setMargin(shadeWidth + 2, shadeWidth + 2 + leftRightSpacing, shadeWidth + 2,
					shadeWidth + 2 + leftRightSpacing);
		} else {
			setMargin(2, 2 + leftRightSpacing, 2, 2 + leftRightSpacing);
		}
	}

	protected void updateCPU() {
		// Determining current cpu usage state
		OperatingSystemMXBean opt = (com.sun.management.OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();

		double cpu;

		cpu = opt.getSystemCpuLoad();
		usedSystemCPU = (long) (cpu * 100);

		cpu = opt.getProcessCpuLoad();
		usedLocalCPU = (long) (cpu * 100);

		// Updating bar text
		setText(getCPUBarText());

		// Updating tooltip text
		if (showTooltip) {
			tooltipLabel.setText(getCPUBarTooltipText());
			tooltip.updateLocation();
		}

		// Updating view
		repaint();
	}

	protected String getCPUBarText() {
		final long total = showMaximumCPU ? maxCPU : usedSystemCPU;
		return usedLocalCPU + "% " + LanguageManager.get("weblaf.ex.memorybar.of") + " " + total + "%";
	}

	protected String getCPUBarTooltipText() {
		return "<html>" + Constants.CPU_LOCAL + " <b>" + usedLocalCPU + "%</b> " + Constants.CPU_SYSTEM + " <b>"
				+ usedSystemCPU + "% " + "</b></html>";
	}

	public int getRefreshRate() {
		return refreshRate;
	}

	public void setRefreshRate(final int refreshRate) {
		this.refreshRate = refreshRate;
		updater.setDelay(refreshRate);
	}

	public int getRound() {
		return round;
	}

	public void setRound(final int round) {
		this.round = round;
	}

	public int getShadeWidth() {
		return shadeWidth;
	}

	public void setShadeWidth(final int shadeWidth) {
		this.shadeWidth = shadeWidth;
		updateBorder();
	}

	public Color getAllocatedBorderColor() {
		return allocatedBorderColor;
	}

	public void setAllocatedBorderColor(final Color allocatedBorderColor) {
		this.allocatedBorderColor = allocatedBorderColor;
	}

	public Color getAllocatedDisabledBorderColor() {
		return allocatedDisabledBorderColor;
	}

	public void setAllocatedDisabledBorderColor(final Color allocatedDisabledBorderColor) {
		this.allocatedDisabledBorderColor = allocatedDisabledBorderColor;
	}

	public int getLeftRightSpacing() {
		return leftRightSpacing;
	}

	public void setLeftRightSpacing(final int leftRightSpacing) {
		this.leftRightSpacing = leftRightSpacing;
		updateBorder();
	}

	public boolean isDrawBorder() {
		return drawBorder;
	}

	public void setDrawBorder(final boolean drawBorder) {
		this.drawBorder = drawBorder;
		updateBorder();
	}

	public boolean isFillBackground() {
		return fillBackground;
	}

	public void setFillBackground(final boolean fillBackground) {
		this.fillBackground = fillBackground;
	}

	public boolean isAllowGcAction() {
		return allowGcAction;
	}

	public void setAllowGcAction(final boolean allowGcAction) {
		this.allowGcAction = allowGcAction;
		if (!allowGcAction && pressed) {
			pressed = false;
			repaint();
		}
	}

	public boolean isShowTooltip() {
		return showTooltip;
	}

	public void setShowTooltip(final boolean showTooltip) {
		this.showTooltip = showTooltip;
		updateTooltip();
	}

	private void updateTooltip() {
		if (showTooltip) {
			tooltip = TooltipManager.setTooltip(this, tooltipLabel, tooltipDelay);
		} else {
			TooltipManager.removeTooltips(tooltipLabel);
		}
	}

	public long getSystemCPU() {
		return usedSystemCPU;
	}

	public long getLocalCPU() {
		return usedLocalCPU;
	}

	public long getMaxCPU() {
		return maxCPU;
	}

	public ImageIcon getCPUIcon() {
		return cpuIcon;
	}

	public void setCPUIcon(final ImageIcon cpuIcon) {
		this.cpuIcon = cpuIcon;
	}

	public int getTooltipDelay() {
		return tooltipDelay;
	}

	public void setTooltipDelay(final int tooltipDelay) {
		this.tooltipDelay = tooltipDelay;
	}

	public boolean isShowMaximumCPU() {
		return showMaximumCPU;
	}

	public void setShowMaximumCPU(final boolean showMaximumCPU) {
		this.showMaximumCPU = showMaximumCPU;
	}

	@Override
	public Shape provideShape() {
		return LafUtils.getWebBorderShape(WebCpuBar.this, getShadeWidth(), getRound());
	}

	@Override
	protected void paintComponent(final Graphics g) {
		final Graphics2D g2d = (Graphics2D) g;
		final Object old = GraphicsUtils.setupAntialias(g2d);

		final boolean enabled = isEnabled();

		// Border and background
		if (drawBorder) {
			LafUtils.drawWebStyle(g2d, this,
					isFocusOwner() ? StyleConstants.fieldFocusColor : StyleConstants.shadeColor, shadeWidth, round,
					fillBackground, !pressed);
		} else if (fillBackground) {
			g2d.setPaint(!pressed ? LafUtils.getWebGradientPaint(0, 0, 0, getHeight()) : getBackground());
			g2d.fill(getVisibleRect());
		}

		// Allocated cpu line
		if (showMaximumCPU) {
			g2d.setPaint(enabled ? allocatedBorderColor : allocatedDisabledBorderColor);
			final int allocatedWidth = getProgressWidth(usedSystemCPU, false);
			g2d.drawLine(shadeWidth + allocatedWidth, shadeWidth + 2, shadeWidth + allocatedWidth,
					getHeight() - shadeWidth - 3);
		}

		// Disabled state background transparency
		final Composite composite = GraphicsUtils.setupAlphaComposite(g2d, 0.5f, !enabled);

		paintInColors(g2d);

		GraphicsUtils.restoreComposite(g2d, composite, !enabled);
		GraphicsUtils.restoreAntialias(g2d, old);

		super.paintComponent(g2d);
	}

	private void paintInColors(Graphics2D g2d) {
		if (usedLocalCPU < 49) {
			// Used cpu background
			g2d.setPaint(minimumFillColor);
			g2d.fill(getProgressShape(usedLocalCPU, true));

			// Used cpu border
			g2d.setPaint(minimumBorderColor);
			g2d.draw(getProgressShape(usedLocalCPU, false));
		} else {
			// Used cpu background
			g2d.setPaint(highFillColor);
			g2d.fill(getProgressShape(usedLocalCPU, true));

			// Used cpu border
			g2d.setPaint(highBorderColor);
			g2d.draw(getProgressShape(usedLocalCPU, false));
		}
	}

	private Shape getProgressShape(final long progress, final boolean fill) {
		final int arcRound = Math.max(0, round - 1) * 2;
		if (drawBorder) {
			return new RoundRectangle2D.Double(shadeWidth + 2, shadeWidth + 2, getProgressWidth(progress, fill),
					getHeight() - 4 - shadeWidth * 2 - (fill ? 0 : 1), arcRound, arcRound);
		} else {
			return new RoundRectangle2D.Double(1, 1, getProgressWidth(progress, fill), getHeight() - 2 - (fill ? 0 : 1),
					arcRound, arcRound);
		}
	}

	private int getProgressWidth(final long progress, final boolean fill) {
		return Math.round((float) (getWidth() - (drawBorder ? 4 + shadeWidth * 2 : 2) - (fill ? 0 : 1)) * progress
				/ (showMaximumCPU ? maxCPU : usedSystemCPU));
	}

	public void addMemoryBarListener(final MemoryBarListener listener) {
		listeners.add(listener);
	}

	public void removeMemoryBarListener(final MemoryBarListener listener) {
		listeners.remove(listener);
	}

	public void fireGcCalled() {
		for (final MemoryBarListener listener : CollectionUtils.copy(listeners)) {
			listener.gcCalled();
		}
	}

	public void fireGcCompleted() {
		for (final MemoryBarListener listener : CollectionUtils.copy(listeners)) {
			listener.gcCompleted();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPreferredWidth() {
		return SizeUtils.getPreferredWidth(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebCpuBar setPreferredWidth(final int preferredWidth) {
		return SizeUtils.setPreferredWidth(this, preferredWidth);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPreferredHeight() {
		return SizeUtils.getPreferredHeight(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebCpuBar setPreferredHeight(final int preferredHeight) {
		return SizeUtils.setPreferredHeight(this, preferredHeight);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinimumWidth() {
		return SizeUtils.getMinimumWidth(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebCpuBar setMinimumWidth(final int minimumWidth) {
		return SizeUtils.setMinimumWidth(this, minimumWidth);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinimumHeight() {
		return SizeUtils.getMinimumHeight(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebCpuBar setMinimumHeight(final int minimumHeight) {
		return SizeUtils.setMinimumHeight(this, minimumHeight);
	}
}