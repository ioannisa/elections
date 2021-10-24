# Demo App
## _Greek National Elections 2019 - Final Results Viewer_

## Features

- The application was made using the Kotlin programming language
- Uses Navigation
- Uses DataBinding
- Uses BindingAdapters to provide kotlin code directly to the layout
- Uses ConstraintLayout heavily to minimize layout nesting and promote performance
- Uses the MVVM architecture
- Provides data through a Repository
- Downloads and parses a custom JSON API that contains election results using Retrofit and Moshi
- Displays the results in a RecyclerView
- Uses DiffUtil in the RecyclerView's Adapter
- Uses Glide to display the party logos
- Utilizes offline cache using Room
- Uses offline first approach
- Uses coroutines
- Saves custom notes at the detail screen of each party to the database
- Contains swipe to refresh functionality

### API
This is one of several custom election APIs I have created for displaying tabular information of election results in multiple formats, for multiple elections, different election types, in various formats.

Try it here:
https://ekloges.crete.gov.gr/exporter/api/tabular_new.php?api_key=YOUR_API_KEY&get=epikrateia&where=epikrateia&is=1&format=json

- **api_key:** [YOUR_API_KEY] is a temporary key that is enabled for demo purposes
- **format:** [json, xml, csv_view, html]
- **get:** what layer to get
- **where:** where the parent layer
- **is:** has the value x

## How it Works

There are a single Activity and two Frames using navigation.

There are three ViewModels.
There is an Activity Level ViewModel that is shared between the two fragments, and each fragment has also its own ViewModel.

The reason behind choosing a common ViewModel is that this one handles the Repository, as we don't want to instantiate multiple repositories, as we make use of the repository in both the MainFragment which displays the RecyclerView, but also at the DetailFragment that allows to "modify data" (as it is inside the requirements), which we use to add notes for the selected party.

We Keep the data in three (3) formats. The Domain, the Network, and the Database. Thus the data is kept in separate formats, which makes testing easier and promotes the separation of concerns.  The ElectionResult contains all three formats, and the functions that map from one format to the other.


