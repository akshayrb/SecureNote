/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package seproject;

//package com.sarf.tts;


/*
import java.util.Locale;
import java.util.*;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class TextSpeaker {
public void TextSpeaker()
 {
 try
 {
   System.setProperty("freetts.voices",
    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

   Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
	System.out.println("enter text");
   Synthesizer  synthesizer =
    Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
   synthesizer.allocate();
   synthesizer.resume();
	Scanner a=new Scanner(System.in);
	String n=a.nextLine();
	System.out.println(n);
   synthesizer.speakPlainText(n, null);
   synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
   synthesizer.deallocate();
  }
   catch(Exception e)
   {
     e.printStackTrace();
   }
 } }*/