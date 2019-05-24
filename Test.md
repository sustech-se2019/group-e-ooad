

## ThreeNewsApplication GUI Test

When the user first open this app, the method `onCreate()` is invoked.

![](/img/test/test-1.jpg)

## NewsEntry GUI Test

`initNewsEntryList()` method is invoked when the main interface showed, the app init the list which stores news entry. 

![](/img/test/test-2.jpg)

## AccountFragment GUI Test

When the account information interface showed, the fragment first invoke `onCreate()`, then invoke `onCreateView()`, then invoke `onViewCreated()`. 

![](/img/test/test-3.jpg)

 Then the user can change its avatar by clicking the `Change Avatar` button. After the button clicked, `showDialog()` first invoked to prompt the user to choose using camera or from the album

![](/img/test/test-4.jpg)

 and return the result to `onAcitivityResult()`, the `cropRawPhoto()` method crop the image to 238 * 238 size, then use `setImageToView()` to show the image.

![](/img/test/test-5.jpg))



![](/img/test/test-6.jpg)

## BrowserFragment GUI Test

When the browser fragment showed, the fragment first invoke `onCreate()`, then invoke `onCreateView()`, then invoke `onCreateOptionsMenu()`.

![](/img/test/test-7.jpg)

When the toolbar's option chose, the method `onOptionsItemSelected()` invoked.



## LoginFragment GUI Test

When the login fragment showed, the fragment first invoke `onCreateView()`.

![](/img/test/test-8.jpg)

![](/img/test/test-9.jpg))

![](/img/test/test-10.jpg)

## RegisterFragment GUI Test

When the register fragment showed, the fragment first invoke `onCreateView()`.

![](/img/test/test-11.jpg)

![](/img/test/test-12.jpg)



## MainActivity GUI Test

This class override a method called `navigateTo()` to navigate to another avtivity or fragment.

(Main menu, the same as the above, successfully navigate.)

## NavigationIconClickListener GUI Test

This class override a method called `onClick()` to use an animator in navigation.

(Without photos, right navigation after push the button.)

## NewsCardRecyclerViewAdapter GUI Test

This class override a method called `onCreateViewHolder()` to create a view holder , and a method called `onBindViewHolder()` to bind this view holder to the main interface

![](/img/test/test-13.jpg)





## NewsCardViewAdapter GUI Test

![](/img/test/test-14.jpg))





## NewsGridFragment GUI Test

When the account information interface showed, the fragment first invoke `onCreate()`, then invoke `onCreateView()`, then invoke `onViewCreated()`.  And invoke `onPause()` when the fragment is paused. Then invoke `onCreateOptionsMenu()`.

When the toolbar's option chose, the method `onOptionsItemSelected()` invoked.

(Main menu, the same as the above, success.)

## NewsGridItemDecoration GUI Test

![](/img/test/test-15.jpg)



Create padding successfully.













