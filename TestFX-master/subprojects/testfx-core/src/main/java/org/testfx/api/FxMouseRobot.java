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

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.Scene;
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

public class FxMouseRobot implements FxRobotMouseInterface {

    private final FxRobotContext context;

    /**
     * Constructs all robot-related implementations and sets {@link #targetPos(Pos)} to {@link Pos#CENTER}.
     */
    public FxMouseRobot() {
        context = new FxRobotContext();
    }

    /**
     * Returns the internal context.
     */
    public FxRobotContext robotContext() {
        return context;
    }

    @Override
    public FxRobot moveBy(double x, double y, Motion motion) {
        context.getMoveRobot().moveBy(x, y, motion);
        return this;
    }

    @Override
    public FxRobot moveTo(PointQuery pointQuery, Motion motion) {
        context.getMoveRobot().moveTo(pointQuery, motion);
        return this;
    }

    @Override
    public FxRobot moveTo(double x, double y, Motion motion) {
        return moveTo(point(new Point2D(x, y)), motion);
    }

    @Override
    public FxRobot moveTo(Point2D point, Motion motion) {
        return moveTo(point(point), motion);
    }

    @Override
    public FxRobot moveTo(Bounds bounds, Motion motion) {
        return moveTo(point(bounds), motion);
    }

    @Override
    public FxRobot moveTo(Node node, Pos offsetReferencePos, Point2D offset, Motion motion) {
        return moveTo(point(node).atPosition(offsetReferencePos).atOffset(offset), motion);
    }

    @Override
    public FxRobot moveTo(Scene scene, Motion motion) {
        return moveTo(point(scene), motion);
    }

    @Override
    public FxRobot moveTo(Window window, Motion motion) {
        return moveTo(point(window), motion);
    }

    @Override
    public FxRobot moveTo(String query, Motion motion) {
        return moveTo(pointOfVisibleNode(query), motion);
    }

    @Override
    public <T extends Node> FxRobot moveTo(Matcher<T> matcher, Motion motion) {
        return moveTo(pointOfVisibleNode(matcher), motion);
    }

    @Override
    public <T extends Node> FxRobot moveTo(Predicate<T> predicate, Motion motion) {
        return moveTo(pointOfVisibleNode(predicate), motion);
    }

    @Override
    @Deprecated
    public FxRobot scroll(int amount) {
        context.getScrollRobot().scroll(amount);
        return this;
    }

    @Override
    public FxRobot scroll(int amount, VerticalDirection direction) {
        context.getScrollRobot().scroll(amount, direction);
        return this;
    }

    @Override
    public FxRobot scroll(VerticalDirection direction) {
        scroll(1, direction);
        return this;
    }

    @Override
    public FxRobot scroll(int amount, HorizontalDirection direction) {
        context.getScrollRobot().scroll(amount, direction);
        return this;
    }

    @Override
    public FxRobot scroll(HorizontalDirection direction) {
        scroll(1, direction);
        return this;
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
            throw new FxRobotException(queryDescription + " returned no nodes.");
        }
        return resultNode.get();
    }

    private Node queryVisibleNode(NodeQuery nodeQuery, String queryDescription) {
        Set<Node> resultNodes = nodeQuery.queryAll();
        if (resultNodes.isEmpty()) {
            throw new FxRobotException(queryDescription + " returned no nodes.");
        }
        Optional<Node> resultNode = from(resultNodes).match(isVisible()).tryQuery();
        if (!resultNode.isPresent()) {
            throw new FxRobotException(queryDescription + " returned " + resultNodes.size() + " nodes" +
                ", but no nodes were visible.");
        }
        return resultNode.get();
    }

}
