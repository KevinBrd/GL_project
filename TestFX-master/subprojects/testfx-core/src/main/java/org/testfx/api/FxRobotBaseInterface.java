/*
 * Copyright 2013-2014 SmartBear Software
 * Copyright 2014-2021 The TestFX Contributors
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the
 * European Commission - subsequent versions of the EUPL (the "Licence"); You may
 * not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 * http://ec.europa.eu/idabc/eupl.html
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the Licence is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the Licence for the
 * specific language governing permissions and limitations under the Licence.
 */
package org.testfx.api;

import java.net.URL;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import javafx.geometry.Bounds;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VerticalDirection;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseButton;
import javafx.stage.Window;

import org.hamcrest.Matcher;
import org.testfx.robot.Motion;
import org.testfx.service.finder.NodeFinder;
import org.testfx.service.finder.WindowFinder;
import org.testfx.service.query.BoundsQuery;
import org.testfx.service.query.NodeQuery;
import org.testfx.service.query.PointQuery;
import org.testfx.service.support.Capture;
import org.testfx.util.WaitForAsyncUtils;

/**
 * Wrapper-like interface that makes it easier to chain together multiple robot methods while adding a number of
 * convenience methods, such as finding a given node, scene or window via a {@link PointQuery}, a {@link Predicate},
 * or a {@link Matcher}.
 */
public interface FxRobotBaseInterface {

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Node)}, clicks the given
     * buttons, and returns itself for method chaining.
     */
    default FxRobotInterface clickOn(Node node, MouseButton... buttons) {
        return clickOn(node, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse directly to the given coordinates, clicks the given buttons, and returns itself
     * for method chaining.
     */
    default FxRobotInterface clickOn(double x, double y, MouseButton... buttons) {
        return clickOn(x, y, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the given coordinates,
     * clicks the given buttons, and returns itself for method chaining.
     */
    FxRobotInterface clickOn(double x, double y, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the given point, clicks the given buttons, and returns itself for
     * method chaining.
     */
    default FxRobotInterface clickOn(Point2D point, MouseButton... buttons) {
        return clickOn(point, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the given point, clicks
     * the given buttons, and returns itself for method chaining.
     */
    FxRobotInterface clickOn(Point2D point, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Bounds)}, clicks the given
     * buttons, and returns itself for method chaining.
     */
    default FxRobotInterface clickOn(Bounds bounds, MouseButton... buttons) {
        return clickOn(bounds, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned
     * from {@link #point(Bounds)}, clicks the given buttons, and returns itself for method chaining.
     */
    FxRobotInterface clickOn(Bounds bounds, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Node)}, clicks the given buttons, and returns itself for method chaining.
     */
    FxRobotInterface clickOn(Node node, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Scene)}, clicks the given
     * buttons, and returns itself for method chaining.
     */
    default FxRobotInterface clickOn(Scene scene, MouseButton... buttons) {
        return clickOn(scene, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Scene)}, clicks the given buttons, and returns itself for method chaining.
     */
    FxRobotInterface clickOn(Scene scene, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Window)}, clicks the given
     * buttons, and returns itself for method chaining.
     */
    default FxRobotInterface clickOn(Window window, MouseButton... buttons) {
        return clickOn(window, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Window)}, clicks the given buttons, and returns itself for method chaining.
     */
    FxRobotInterface clickOn(Window window, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(String)}, clicks the given
     * buttons, and returns itself for method chaining.
     */
    default FxRobotInterface clickOn(String query, MouseButton... buttons) {
        return clickOn(query, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(String)}, clicks the given buttons, and returns itself for method chaining.
     */
    FxRobotInterface clickOn(String query, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Matcher)}, clicks the given
     * buttons, and returns itself for method chaining.
     */
    default <T extends Node> FxRobotInterface clickOn(Matcher<T> matcher, MouseButton... buttons) {
        return clickOn(matcher, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Matcher)}, clicks the given buttons, and returns itself for method chaining.
     */
    <T extends Node> FxRobotInterface clickOn(Matcher<T> matcher, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Predicate)}, clicks the given
     * buttons, and returns itself for method chaining.
     */
    default <T extends Node> FxRobotInterface clickOn(Predicate<T> predicate, MouseButton... buttons) {
        return clickOn(predicate, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Predicate)}, clicks the given buttons, and returns itself for method chaining.
     */
    <T extends Node> FxRobotInterface clickOn(Predicate<T> predicate, Motion motion, MouseButton... buttons);

    /**
     * Calls {@link org.testfx.robot.ClickRobot#clickOn(MouseButton...)} and returns itself for more method chaining.
     */
    FxRobotInterface clickOn(MouseButton... buttons);

    /**
     * Calls {@link org.testfx.robot.ClickRobot#clickOn(PointQuery, Motion, MouseButton...)} and returns itself for
     * more method chaining.
     */
    default FxRobotInterface clickOn(PointQuery pointQuery, MouseButton... buttons) {
        return clickOn(pointQuery, Motion.DEFAULT, buttons);
    }

    /**
     * Calls {@link org.testfx.robot.ClickRobot#clickOn(PointQuery, MouseButton...)} and returns itself for more method
     * chaining.
     */
    FxRobotInterface clickOn(PointQuery pointQuery, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Clicks the {@link MouseButton#SECONDARY} button and returns itself for method chaining.
     */
    FxRobotInterface rightClickOn();

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Node)}, clicks
     * the {@link MouseButton#SECONDARY} button, and returns itself for method chaining.
     */
    default FxRobotInterface rightClickOn(Node node) {
        return rightClickOn(node, Motion.DEFAULT);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Node)}, clicks the {@link MouseButton#SECONDARY} button, and returns itself for method chaining.
     */
    FxRobotInterface rightClickOn(Node node, Motion motion);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link PointQuery#query()}, clicks
     * the {@link MouseButton#SECONDARY} button and returns itself for method chaining.
     */
    default FxRobotInterface rightClickOn(PointQuery pointQuery) {
        return rightClickOn(pointQuery, Motion.DEFAULT);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link PointQuery#query()}, clicks the {@link MouseButton#SECONDARY} button and returns itself for method
     * chaining.
     */
    FxRobotInterface rightClickOn(PointQuery pointQuery, Motion motion);

    /**
     * Convenience method: Moves mouse directly to the given coordinates, clicks the {@link MouseButton#SECONDARY}
     * button, and returns itself for method chaining.
     */
    default FxRobotInterface rightClickOn(double x, double y) {
        return rightClickOn(x, y, Motion.DEFAULT);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the given coordinates,
     * clicks the {@link MouseButton#SECONDARY} button, and returns itself for method chaining.
     */
    FxRobotInterface rightClickOn(double x, double y, Motion motion);

    /**
     * Convenience method: Moves mouse to the point returned from {@link #point(Point2D)}, clicks
     * the {@link MouseButton#SECONDARY} button, and returns itself for method chaining.
     */
    default FxRobotInterface rightClickOn(Point2D point) {
        return rightClickOn(point, Motion.DEFAULT);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Point2D)}, clicks the {@link MouseButton#SECONDARY} button, and returns itself for method
     * chaining.
     */
    FxRobotInterface rightClickOn(Point2D point, Motion motion);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Bounds)}, clicks
     * the {@link MouseButton#SECONDARY} button, and returns itself for method chaining.
     */
    default FxRobotInterface rightClickOn(Bounds bounds) {
        return rightClickOn(bounds, Motion.DEFAULT);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Bounds)}, clicks the {@link MouseButton#SECONDARY} button, and returns itself for method chaining.
     */
    FxRobotInterface rightClickOn(Bounds bounds, Motion motion);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Scene)}, clicks
     * the {@link MouseButton#SECONDARY} button, and returns itself for method chaining.
     */
    default FxRobotInterface rightClickOn(Scene scene) {
        return rightClickOn(scene, Motion.DEFAULT);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Scene)}, clicks the {@link MouseButton#SECONDARY} button, and returns itself for method chaining.
     */
    FxRobotInterface rightClickOn(Scene scene, Motion motion);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Window)}, clicks
     * the {@link MouseButton#SECONDARY} button, and returns itself for method chaining.
     */
    default FxRobotInterface rightClickOn(Window window) {
        return rightClickOn(window, Motion.DEFAULT);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Window)}, clicks the {@link MouseButton#SECONDARY} button, and returns itself for method chaining.
     */
    FxRobotInterface rightClickOn(Window window, Motion motion);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(String)}, clicks
     * the {@link MouseButton#SECONDARY} button, and returns itself for method chaining.
     */
    default FxRobotInterface rightClickOn(String query) {
        return rightClickOn(query, Motion.DEFAULT);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(String)}, clicks the {@link MouseButton#SECONDARY} button, and returns itself for method chaining.
     */
    FxRobotInterface rightClickOn(String query, Motion motion);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Matcher)}, clicks
     * the {@link MouseButton#SECONDARY} button, and returns itself for method chaining.
     */
    default <T extends Node> FxRobotInterface rightClickOn(Matcher<T> matcher) {
        return rightClickOn(matcher, Motion.DEFAULT);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Matcher)}, clicks the {@link MouseButton#SECONDARY} button, and returns itself for method
     * chaining.
     */
    <T extends Node> FxRobotInterface rightClickOn(Matcher<T> matcher, Motion motion);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Predicate)}, clicks
     * the {@link MouseButton#SECONDARY} button, and returns itself for method chaining.
     */
    default <T extends Node> FxRobotInterface rightClickOn(Predicate<T> predicate) {
        return rightClickOn(predicate, Motion.DEFAULT);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Predicate)}, clicks the {@link MouseButton#SECONDARY} button, and returns itself for method
     * chaining.
     */
    <T extends Node> FxRobotInterface rightClickOn(Predicate<T> predicate, Motion motion);

    /**
     * Calls {@link org.testfx.robot.ClickRobot#doubleClickOn(MouseButton...)} and returns itself for more method
     * chaining.
     */
    FxRobotInterface doubleClickOn(MouseButton... buttons);

    /**
     * Calls {@link org.testfx.robot.ClickRobot#doubleClickOn(PointQuery, Motion, MouseButton...)} and returns itself
     * for method chaining.
     */
    default FxRobotInterface doubleClickOn(PointQuery pointQuery, MouseButton... buttons) {
        return doubleClickOn(pointQuery, Motion.DEFAULT, buttons);
    }

    /**
     * Calls {@link org.testfx.robot.ClickRobot#doubleClickOn(PointQuery, Motion, MouseButton...)} and returns itself
     * for method chaining.
     */
    FxRobotInterface doubleClickOn(PointQuery pointQuery, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(double, double)}, double
     * clicks the given buttons, and returns itself for method chaining.
     */
    default FxRobotInterface doubleClickOn(double x, double y, MouseButton... buttons) {
        return doubleClickOn(x, y, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(double, double)}, double clicks the given buttons, and returns itself for method chaining.
     */
    FxRobotInterface doubleClickOn(double x, double y, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Point2D)}, double
     * clicks the given buttons, and returns itself for method chaining.
     */
    default FxRobotInterface doubleClickOn(Point2D point, MouseButton... buttons) {
        return doubleClickOn(point, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Point2D)}, double clicks the given buttons, and returns itself for method chaining.
     */
    FxRobotInterface doubleClickOn(Point2D point, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Bounds)}, double
     * clicks the given buttons, and returns itself for method chaining.
     */
    default FxRobotInterface doubleClickOn(Bounds bounds, MouseButton... buttons) {
        return doubleClickOn(bounds, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Bounds)}, double clicks the given buttons, and returns itself for method chaining.
     */
    FxRobotInterface doubleClickOn(Bounds bounds, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Node)}, double
     * clicks the given buttons, and returns itself for method chaining.
     */
    default FxRobotInterface doubleClickOn(Node node, MouseButton... buttons) {
        return doubleClickOn(node, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Node)}, double clicks the given buttons, and returns itself for method chaining.
     */
    FxRobotInterface doubleClickOn(Node node, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Scene)}, double
     * clicks the given buttons, and returns itself for method chaining.
     */
    default FxRobotInterface doubleClickOn(Scene scene, MouseButton... buttons) {
        return doubleClickOn(scene, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Scene)}, double clicks the given buttons, and returns itself for method chaining.
     */
    FxRobotInterface doubleClickOn(Scene scene, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Window)}, double
     * clicks the given buttons, and returns itself for method chaining.
     */
    default FxRobotInterface doubleClickOn(Window window, MouseButton... buttons) {
        return doubleClickOn(window, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Window)}, double clicks the given buttons, and returns itself for method chaining.
     */
    FxRobotInterface doubleClickOn(Window window, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(String)}, double
     * clicks the given buttons, and returns itself for method chaining.
     */
    default FxRobotInterface doubleClickOn(String query, MouseButton... buttons) {
        return doubleClickOn(query, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(String)}, double clicks the given buttons, and returns itself for method chaining.
     */
    FxRobotInterface doubleClickOn(String query, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Matcher)}, double
     * clicks the given buttons, and returns itself for method chaining.
     */
    default <T extends Node> FxRobotInterface doubleClickOn(Matcher<T> matcher, MouseButton... buttons) {
        return doubleClickOn(matcher, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Matcher)}, double clicks the given buttons, and returns itself for method chaining.
     */
    <T extends Node> FxRobotInterface doubleClickOn(Matcher<T> matcher, Motion motion, MouseButton... buttons);

    /**
     * Convenience method: Moves mouse directly to the point returned from {@link #point(Predicate)}, double
     * clicks the given buttons, and returns itself for method chaining.
     */
    default <T extends Node> FxRobotInterface doubleClickOn(Predicate<T> predicate, MouseButton... buttons) {
        return doubleClickOn(predicate, Motion.DEFAULT, buttons);
    }

    /**
     * Convenience method: Moves mouse using the given {@code motion} (see: {@link Motion} to the point returned from
     * {@link #point(Predicate)}, double clicks the given buttons, and returns itself for method chaining.
     */
    <T extends Node> FxRobotInterface doubleClickOn(Predicate<T> predicate, Motion motion, MouseButton... buttons);
    //...

}


