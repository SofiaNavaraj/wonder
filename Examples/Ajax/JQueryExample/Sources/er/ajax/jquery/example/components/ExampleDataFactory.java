package er.ajax.jquery.example.components;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.extensions.foundation.ERXFileUtilities;

public class ExampleDataFactory {

	private static NSMutableArray<Word> _exampleData;

	public static NSMutableArray<Word> randomWords(int count) {
		NSMutableArray<Word> words = new NSMutableArray<Word>();
		Random rand = new Random();
		for (int i = 0; i < count; i++) {
			String s = "";
			int max = 6 + rand.nextInt(6);
			for (int j = 0; j < max; j++) {
				int c = rand.nextInt(26);
				s += Character.toString((char) ('A' + c));
			}
			Word example = new Word(s);
			words.addObject(example);
		}
		return words;
	}

	public static synchronized NSArray<Word> someWords(int count) {
		NSArray<Word> allWords = allWords();
		NSMutableArray<Word> someWords = new NSMutableArray<Word>();
		for (int i = 0; i < count; i ++) {
			someWords.addObject(allWords.objectAtIndex((int)(Math.random() * allWords.count())));
		}
		return someWords;
	}

	public static synchronized NSArray<Word> allWords() {
		// some sample data. if we don't find the file, just create random strings
		if (_exampleData == null) {
			_exampleData = new NSMutableArray<Word>();
			File f = new File("/usr/share/dict/words");
			if (f.exists()) {
				try {
					String words = ERXFileUtilities.stringFromFile(f);
					String splitWords[] = words.split("\\n");
					for (int i = 0; i < splitWords.length; i++) {
						String word = splitWords[i];
						// we don't want all words, that just takes too long
						if (word.length() > 0 && ((i % 20) == 0)) {
							Word example = new Word(word);
							_exampleData.addObject(example);
						}
					}
				}
				catch (IOException e) {
					System.out.println("ExampleUtils.exampleValues: Can't read " + f + ": " + e);
				}
			}
			if (_exampleData.count() == 0) {
				_exampleData.addObjectsFromArray(ExampleDataFactory.randomWords(1000));
			}
			EOSortOrdering.sortArrayUsingKeyOrderArray(_exampleData, new NSArray<EOSortOrdering>(EOSortOrdering.sortOrderingWithKey("name", EOSortOrdering.CompareAscending)));

		}
		return _exampleData.immutableClone();
	}

	public static NSMutableArray<String> values(String prefix, int count) {
		NSMutableArray<String> values = new NSMutableArray<String>();
		for (int i = 0; i < count; i++) {
			values.addObject(prefix + i);
		}
		return values;
	}

}
