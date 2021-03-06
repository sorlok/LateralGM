/*
 * Copyright (C) 2007 Quadduc <quadduc@gmail.com>
 * 
 * This file is part of LateralGM.
 * LateralGM is free software and comes with ABSOLUTELY NO WARRANTY.
 * See LICENSE for details.
 */

package org.lateralgm.components.impl;

import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;

import org.lateralgm.main.PrefsStore;

public class FramePrefsHandler implements ComponentListener,WindowStateListener
	{
	private final JFrame frame;

	public FramePrefsHandler(JFrame frame)
		{
		this.frame = frame;
		frame.pack(); // makes the frame displayable, so that maximizing works
		frame.setMinimumSize(frame.getSize());
		frame.setMaximizedBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		frame.setBounds(PrefsStore.getWindowBounds());
		int state = frame.getExtendedState()
				| (PrefsStore.getWindowMaximized() ? JFrame.MAXIMIZED_BOTH : 0);
		frame.setExtendedState(state);
		frame.addComponentListener(this);
		frame.addWindowStateListener(this);
		}

	private boolean isMaximized()
		{
		return (frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH;
		}

	public void componentMoved(ComponentEvent e)
		{
		if (!isMaximized()) PrefsStore.setWindowBounds(frame.getBounds());
		}

	public void componentResized(ComponentEvent e)
		{
		if (!isMaximized()) PrefsStore.setWindowBounds(frame.getBounds());
		}

	public void windowStateChanged(WindowEvent e)
		{
		PrefsStore.setWindowMaximized(isMaximized());
		}

	public void componentHidden(ComponentEvent e)
		{
		//Unused
		}

	public void componentShown(ComponentEvent e)
		{
		//Unused
		}
	}
