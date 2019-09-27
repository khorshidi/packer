package com.mobiquityinc;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PackerTest {

    @Test
    public void pack_simple_multi_line() throws Exception {
        String actual = Packer.pack(absolutePath("simple_multi_line"));
        assertValidOutput(actual, "4\n-\n2,7\n8,9");
    }

    @Test
    public void pack_simple_single_line() throws Exception {
        String actual = Packer.pack(absolutePath("simple_single_line"));
        assertValidOutput(actual, "8,9");
    }

    @Test
    public void pack_simple_empty() throws Exception {
        String pack = Packer.pack(absolutePath("simple_empty"));
        Assert.assertEquals(pack, "");
    }

    @Test(expected = APIException.class)
    public void pack_zero_capacity() throws Exception {
        Packer.pack(absolutePath("scenario_with_zero_capacity"));
    }

    @Test(expected = APIException.class)
    public void pack_scenario_with_negative_capacity() throws Exception {
        Packer.pack(absolutePath("scenario_with_negative_capacity"));
    }

    @Test(expected = APIException.class)
    public void pack_scenario_with_greater_than_100_capacity() throws Exception {
        Packer.pack(absolutePath("scenario_with_greater_than_100_capacity"));
    }

    @Test(expected = APIException.class)
    public void pack_scenario_with_more_than_15_items() throws Exception {
        Packer.pack(absolutePath("scenario_with_more_than_15_items"));
    }

    @Test(expected = APIException.class)
    public void pack_scenario_with_invalid_capacity() throws Exception {
        Packer.pack(absolutePath("scenario_with_invalid_capacity"));
    }

    @Test(expected = APIException.class)
    public void pack_line_with_invalid_capacity_delimiter() throws Exception {
        Packer.pack(absolutePath("line_with_invalid_capacity_delimiter"));
    }

    @Test(expected = APIException.class)
    public void pack_item_with_zero_weight() throws Exception {
        Packer.pack(absolutePath("item_with_zero_weight"));
    }

    @Test(expected = APIException.class)
    public void pack_item_with_greater_than_100_weight() throws Exception {
        Packer.pack(absolutePath("item_with_greater_than_100_weight"));
    }

    @Test(expected = APIException.class)
    public void pack_item_with_greater_than_101_cost() throws Exception {
        Packer.pack(absolutePath("item_with_greater_than_100_cost"));
    }

    @Test(expected = APIException.class)
    public void pack_scenario_with_no_item() throws Exception {
        Packer.pack(absolutePath("scenario_with_no_item"));
    }

    @Test(expected = APIException.class)
    public void pack_item_with_invalid_cost() throws Exception {
        Packer.pack(absolutePath("item_with_invalid_cost"));
    }

    @Test(expected = APIException.class)
    public void pack_item_with_invalid_weight() throws Exception {
        Packer.pack(absolutePath("item_with_invalid_weight"));
    }

    @Test(expected = APIException.class)
    public void pack_item_with_invalid_index() throws Exception {
        Packer.pack(absolutePath("item_with_invalid_index"));
    }

    @Test(expected = APIException.class)
    public void testPackWithFileNoteExist() throws Exception {
        Packer.pack("/invalid/path");
    }

    private String absolutePath(String s) {
        return new File("src/test/resources/" + s).getAbsolutePath();
    }

    private void assertValidOutput(String actual, String expected) {
        Set<Set<String>> act = Stream.of(actual.split("\n"))
                .map(a -> Stream.of(a.split(",")).collect(Collectors.toSet()))
                .collect(Collectors.toSet());
        Set<Set<String>> exp = Stream.of(expected.split("\n"))
                .map(a -> Stream.of(a.split(",")).collect(Collectors.toSet()))
                .collect(Collectors.toSet());
        Assert.assertEquals(act, exp);
    }

}
