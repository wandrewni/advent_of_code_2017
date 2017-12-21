package org.wandotini;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DayOneCaptchaTest {
    private DayOneCaptchaParser captcha;

    @Before
    public void setUp() throws Exception {
        captcha = new DayOneCaptcha();
    }

    @Test
    public void singleDigitStringIsZero() throws Exception {
        checkCaptcha("1", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroLengthStringthrowsIllegalArgumentException() throws Exception {
        captcha.parse("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullStringThrowsIllegalArgumentException() throws Exception {
        captcha.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonDigitsThrowsIlegalArgumentException() throws Exception {
        captcha.parse("nondigits");
    }

    @Test
    public void firstDigitMatchesSecond() throws Exception {
        checkCaptcha("112", 1);
    }

    @Test
    public void secondDigitMatchesThird() throws Exception {
        checkCaptcha("3112", 1);
    }

    @Test
    public void thirdDigitMatchesFourth() throws Exception {
        checkCaptcha("32112", 1);
    }

    @Test
    public void canMatchDigitsGreaterThanOne() throws Exception {
        checkCaptcha("32332", 3);
    }

    @Test
    public void canDetectLastDigitMatchingFirst() throws Exception {
        checkCaptcha("232", 2);
    }

    @Test
    public void canFindMultipleEligibleDigits() throws Exception {
        checkCaptcha("233233", 6);
    }

    @Test
    public void canFindMultipleEligibleDigitsIncludingLastDigit() throws Exception {
        checkCaptcha("233232", 5);
    }

    @Test
    public void theCaptcha() throws Exception {
        String input = "42812249899758728399611695139795793356913694984837941712536253226986946118574311373399233137" +
                "98564463624821296465562866115437565642757153598749248981134244727829747894643486262785329362" +
                "28881786273586278886575828239366794429223317476722337424399239986153675275924113322561873814" +
                "36445133918691881345168526319289162718676981812871559571544456544458151467752187493594291354" +
                "71217518516313733161224914715646977312989519895119172726843354634362182832619621586712666252" +
                "99188764589814518793576375629163896349665312991285776595142146261792244475721782941364787968" +
                "92453784169853828845935515978398563818725465385186487454458487899919324264161185975672863462" +
                "38534756384789237444715638456354681738241966843619342694594591242691968115129274426627615638" +
                "24323621758785866391424778683599179447845595931928589255935953295111937431266815352781399967" +
                "29538933962617866414841556117538672599246978288875794255836211793862936912943971742747441685" +
                "16281211916393556463942764518471311826524865614159428158187858845591934838781393518416333663" +
                "98788657844396925423217662517356486193821341454889283266691224778723833397914224396722559593" +
                "95912531717589959468552485241949579338948183135478728745236714566182928751877163193931468313" +
                "77224935313181813152163429941416834841119694769529463783148834216779523975886135629587413289" +
                "87734565492378977396431481215983656814486518865642645612413945129485464979535991675776338786" +
                "75899712812465131115318281618892493518636181379725199764399268629472469928196947314272111643" +
                "29682164349776841381844819638451414867939964767939542262258854324226543944398828421632954585" +
                "49755137247614338991879966665925466545111899714943716571113326479432925939227996799951279485" +
                "72283675445773766819184591456673228592845378181879223644781612749244599394589443569279983921" +
                "74672539862182131312497868333339363322577951919379426886681826294891916931541841773981864624" +
                "81316834678733713614889439352976144726162214648922159719979143735815478633912633185334529484" +
                "77932281861143819452229227878765376332894442151656918117851791574562529515861163636525394845" +
                "5727653672922299582352766484";
        checkCaptcha(input, 1034);
    }

    private void checkCaptcha(String input, int result) {
        assertThat(captcha.parse(input), is(result));
    }
}