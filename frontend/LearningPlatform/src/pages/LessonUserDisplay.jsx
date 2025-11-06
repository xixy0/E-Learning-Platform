import { Outlet, useParams } from 'react-router-dom';
import LessonSidebarUser from '../components/Student/LessonSidebarUser';

function LessonUserDisplay() {

    const{courseId} = useParams();
   return (
    <div className="flex min-h-screen  bg-gradient-to-br from-blue-50 to-blue-100">
     
      <div className="w-64">
      <LessonSidebarUser courseId = {courseId} />
      </div>

      {/* Main content area */}
      <main className="flex-1 p-8 overflow-y-auto">
        <Outlet /> 
        {/* Outlet renders the child route’s content here */}
      </main>
    </div>
  );
}

export default LessonUserDisplay