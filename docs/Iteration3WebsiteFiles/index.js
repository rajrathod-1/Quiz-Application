// Define the download function
function downloadAPK() {
    var apkPath = './app/quizmonkies.apk';
    
    // Create a virtual anchor element
    var link = document.createElement('a');
    link.href = apkPath;
    link.download = 'quizmonkies.apk';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}


function openArchitectureImage() {
    // Open a new window with the image
    var architectureImagePath = './images/Architecture_Iteration_3.drawio.png';
    window.open(architectureImagePath, '_blank', 'width=800,height=600');
  }

function openProjectVelocityImage() {
// Open a new window with the image
var architectureImagePath = './images/velocity.png';
window.open(architectureImagePath, '_blank', 'width=800,height=600');
}
  

  