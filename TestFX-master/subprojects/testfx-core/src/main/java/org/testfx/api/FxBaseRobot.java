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
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseButton;
import javafx.stage.Window;

import org.hamcrest.Matcher;
import org.testfx.robot.Motion;
import org.testfx.service.locator.PointLocator;
import org.testfx.service.query.BoundsQuery;
import org.testfx.service.query.NodeQuery;
import org.testfx.service.query.PointQuery;
import org.testfx.service.support.Capture;
import org.testfx.util.BoundsQueryUtils;

import static org.testfx.util.NodeQueryUtils.isVisible;
import static org.testfx.util.WaitForAsyncUtils.asyncFx;
import static org.testfx.util.WaitForAsyncUtils.waitFor;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class FxBaseRobot implements FxBaseRobotBaseInterface {

    private final FxRobotContext context;

    /**
     * Constructs all robot-related implementations and sets {@link #targetPos(Pos)} to {@link Pos#CENTER}.
     */
    public FxBaseRobot() {
        context = new FxRobotContext();
    }

    /**
     * Returns the internal context.
     */
    public FxRobotContext robotContext() {
        return context;
    }

    @Override
    public FxBaseRobot clickOn(MouseButton... buttons) {
        context.getClickRobot().clickOn(buttons);
        return this;
    }

    @Override
    public FxBaseRobot clickOn(PointQuery pointQuery, Motion motion, MouseButton... buttons) {
        context.getClickRobot().clickOn(pointQuery, motion, buttons);
        return this;
    }

    @Override
    public FxBaseRobot clickOn(double x, double y, Motion motion, MouseButton... buttons) {
        return clickOn(point(x, y), motion, buttons);
    }

    @Override
    public FxBaseRobot clickOn(Point2D point, Motion motion, MouseButton... buttons) {
        return clickOn(point(point), motion, buttons);
    }

    @Override
    public FxBaseRobot clickOn(Bounds bounds, Motion motion, MouseButton... buttons) {
        return clickOn(point(bounds), motion, buttons);
    }

    @Override
    public FxBaseRobot clickOn(Node node, Motion motion, MouseButton... buttons) {
        return clickOn(point(node), motion, buttons);
    }

    @Override
    public FxBaseRobot clickOn(Scene scene, Motion motion, MouseButton... buttons) {
        return clickOn(point(scene), motion, buttons);
    }

    @Override
    public FxBaseRobot clickOn(Window window, Motion motion, MouseButton... buttons) {
        return clickOn(point(window), motion, buttons);
    }

    @Override
    public FxBaseRobot clickOn(String query, Motion motion, MouseButton... buttons) {
        return clickOn(pointOfVisibleNode(query), motion, buttons);
    }

    @Override
    public <T extends Node> FxBaseRobot clickOn(Matcher<T> matcher, Motion motion, MouseButton... buttons) {
        return clickOn(pointOfVisibleNode(matcher), motion, buttons);
    }

    @Override
    public <T extends Node> FxBaseRobot clickOn(Predicate<T> predicate, Motion motion, MouseButton... buttons) {
        return clickOn(pointOfVisibleNode(predicate), motion, buttons);
    }

    @Override
    public FxBaseRobot rightClickOn() {
        return clickOn(MouseButton.SECONDARY);
    }

    @Override
    public FxBaseRobot rightClickOn(PointQuery pointQuery, Motion motion) {
        return clickOn(pointQuery, motion, MouseButton.SECONDARY);
    }

    @Override
    public FxBaseRobot rightClickOn(double x, double y, Motion motion) {
        return clickOn(x, y, motion, MouseButton.SECONDARY);
    }

    @Override
    public FxBaseRobot rightClickOn(Point2D point, Motion motion) {
        return clickOn(point, motion, MouseButton.SECONDARY);
    }

    @Override
    public FxBaseRobot rightClickOn(Bounds bounds, Motion motion) {
        return clickOn(bounds, motion, MouseButton.SECONDARY);
    }

    @Override
    public FxBaseRobot rightClickOn(Node node, Motion motion) {
        return clickOn(node, motion, MouseButton.SECONDARY);
    }

    @Override
    public FxBaseRobot rightClickOn(Scene scene, Motion motion) {
        return clickOn(scene, motion, MouseButton.SECONDARY);
    }

    @Override
    public FxBaseRobot rightClickOn(Window window, Motion motion) {
        return clickOn(window, motion, MouseButton.SECONDARY);
    }

    @Override
    public FxBaseRobot rightClickOn(String query, Motion motion) {
        return clickOn(query, motion, MouseButton.SECONDARY);
    }

    @Override
    public <T extends Node> FxBaseRobot rightClickOn(Matcher<T> matcher, Motion motion) {
        return clickOn(matcher, motion, MouseButton.SECONDARY);
    }

    @Override
    public <T extends Node> FxBaseRobot rightClickOn(Predicate<T> predicate, Motion motion) {
        return clickOn(predicate, motion, MouseButton.SECONDARY);
    }

    @Override
    public FxBaseRobot doubleClickOn(MouseButton... buttons) {
        context.getClickRobot().doubleClickOn(buttons);
        return this;
    }

    @Override
    public FxBaseRobot doubleClickOn(PointQuery pointQuery, Motion motion, MouseButton... buttons) {
        context.getClickRobot().doubleClickOn(pointQuery, motion, buttons);
        return this;
    }

    @Override
    public FxBaseRobot doubleClickOn(double x, double y, Motion motion, MouseButton... buttons) {
        return doubleClickOn(point(x, y), motion, buttons);
    }

    @Override
    public FxBaseRobot doubleClickOn(Point2D point, Motion motion, MouseButton... buttons) {
        return doubleClickOn(point(point), motion, buttons);
    }

    @Override
    public FxBaseRobot doubleClickOn(Bounds bounds, Motion motion, MouseButton... buttons) {
        return doubleClickOn(point(bounds), motion, buttons);
    }

    @Override
    public FxBaseRobot doubleClickOn(Node node, Motion motion, MouseButton... buttons) {
        return doubleClickOn(point(node), motion, buttons);
    }

    @Override
    public FxBaseRobot doubleClickOn(Scene scene, Motion motion, MouseButton... buttons) {
        return doubleClickOn(point(scene), motion, buttons);
    }

    @Override
    public FxBaseRobot doubleClickOn(Window window, Motion motion, MouseButton... buttons) {
        return doubleClickOn(point(window), motion, buttons);
    }

    @Override
    public FxBaseRobot doubleClickOn(String query, Motion motion, MouseButton... buttons) {
        return doubleClickOn(pointOfVisibleNode(query), motion, buttons);
    }

    @Override
    public <T extends Node> FxBaseRobot doubleClickOn(Matcher<T> matcher, Motion motion, MouseButton... buttons) {
        return doubleClickOn(pointOfVisibleNode(matcher), motion, buttons);
    }

    @Override
    public <T extends Node> FxBaseRobot doubleClickOn(Predicate<T> predicate, Motion motion, MouseButton... buttons) {
        return doubleClickOn(pointOfVisibleNode(predicate), motion, buttons);
    }

    private PointQuery pointOfVisibleNode(String query) {
        NodeQuery nodeQuery = lookup(query);
        Node node = queryVisibleNode(nodeQuery, "the query \"" + query + "\"");
        return point(node);
    }

    private <T extends Node> PointQuery pointOfVisibleNode(Matcher<T> matcher) {
        NodeQuery nodeQuery = lookup(matcher);
        Node node = queryVisibleNode(nodeQuery, "the matcher \"" + matcher.toString() + "\"");
        return point(node);
    }

    private <T extends Node> PointQuery pointOfVisibleNode(Predicate<T> predicate) {
        NodeQuery nodeQuery = lookup(predicate);
        Node node = queryVisibleNode(nodeQuery, "the predicate");
        return point(node);
    }

    private Node queryNode(NodeQuery nodeQuery, String queryDescription) {
        Optional<Node> resultNode = nodeQuery.tryQuery();
        if (!resultNode.isPresent()) {
            throw new FxBaseRobotException(queryDescription + " returned no nodes.");
        }
        return resultNode.get();
    }

    private Node queryVisibleNode(NodeQuery nodeQuery, String queryDescription) {
        Set<Node> resultNodes = nodeQuery.queryAll();
        if (resultNodes.isEmpty()) {
            throw new FxBaseRobotException(queryDescription + " returned no nodes.");
        }
        Optional<Node> resultNode = from(resultNodes).match(isVisible()).tryQuery();
        if (!resultNode.isPresent()) {
            throw new FxBaseRobotException(queryDescription + " returned " + resultNodes.size() + " nodes" +
                ", but no nodes were visible.");
        }
        return resultNode.get();
    }

}
